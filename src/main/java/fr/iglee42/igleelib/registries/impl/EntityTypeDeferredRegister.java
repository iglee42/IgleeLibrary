package fr.iglee42.igleelib.registries.impl;

import com.mojang.logging.LogUtils;

import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntityTypeDeferredRegister extends ExtendedDeferredRegister<EntityType<?>> {

	private static final Logger LOGGER = LogUtils.getLogger();

	private Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> livingEntityAttributes = new HashMap<>();
	private Map<Supplier<? extends EntityType<? extends LivingEntity>>, SpawnPlacementData<?>> livingEntityPlacements = new HashMap<>();

	public EntityTypeDeferredRegister(String modid) {
		super(Registries.ENTITY_TYPE, modid);
	}

	public <ENTITY extends Monster> ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> registerBasicMonster(String name,
                                                                                                                   Supplier<EntityType.Builder<ENTITY>> builder, Supplier<AttributeSupplier.Builder> attributes) {
		return registerBasicPlacement(name, builder, attributes, Monster::checkMonsterSpawnRules);
	}

	public <ENTITY extends LivingEntity> ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> registerBasicPlacement(String name,
                                                                                                                          Supplier<EntityType.Builder<ENTITY>> builder, Supplier<AttributeSupplier.Builder> attributes, SpawnPlacements.SpawnPredicate<ENTITY> placementPredicate) {
		return register(name, builder, attributes, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, placementPredicate,
				RegisterSpawnPlacementsEvent.Operation.AND);
	}

	public <ENTITY extends LivingEntity> ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> register(String name, Supplier<EntityType.Builder<ENTITY>> builder,
                                                                                                            Supplier<AttributeSupplier.Builder> attributes, @Nullable SpawnPlacementType placementType, @Nullable Heightmap.Types heightmap,
                                                                                                            SpawnPlacements.SpawnPredicate<ENTITY> placementPredicate, RegisterSpawnPlacementsEvent.Operation placementOperation) {
		ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> holder = register(name, builder, attributes);
		livingEntityPlacements.put(holder, new SpawnPlacementData<>(placementType, heightmap, placementPredicate, placementOperation));
		return holder;
	}

	public <ENTITY extends LivingEntity> ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> register(String name, Supplier<EntityType.Builder<ENTITY>> builder,
                                                                                                            Supplier<AttributeSupplier.Builder> attributes) {
		ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> holder = registerBuilder(name, builder);
		livingEntityAttributes.put(holder, attributes);
		return holder;
	}

	public <ENTITY extends Entity> ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> registerBuilder(String name, Supplier<EntityType.Builder<ENTITY>> builder) {
		return register(name, () -> builder.get().build(name));
	}

	public <ENTITY extends Entity> ExtendedDeferredHolder<EntityType<?>, EntityType<ENTITY>> registerBuilder(String name, EntityType.EntityFactory<ENTITY> factory,
                                                                                                             MobCategory category, java.util.function.UnaryOperator<EntityType.Builder<ENTITY>> modifier) {
		return registerBuilder(name, () -> modifier.apply(EntityType.Builder.of(factory, category)));
	}

	@Override
	public void register(@NotNull IEventBus bus) {
		super.register(bus);
		bus.addListener(this::registerEntityAttributes);
		bus.addListener(this::registerPlacements);
	}

	private void registerEntityAttributes(EntityAttributeCreationEvent event) {
		if (livingEntityAttributes == null) {
			LOGGER.error("Entity attributes have already been created");
			return;
		}
		for (Map.Entry<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> entry : livingEntityAttributes.entrySet()) {
			event.put(entry.getKey().get(), entry.getValue().get().build());
		}
		livingEntityAttributes = null;
	}

	private void registerPlacements(RegisterSpawnPlacementsEvent event) {
		if (livingEntityPlacements == null) {
			LOGGER.error("Entity placements have already been set");
			return;
		}
		for (Map.Entry<Supplier<? extends EntityType<? extends LivingEntity>>, SpawnPlacementData<?>> entry : livingEntityPlacements.entrySet()) {
			entry.getValue().register(event, entry.getKey().get());
		}
		livingEntityPlacements = null;
	}

	private record SpawnPlacementData<ENTITY extends LivingEntity>(@Nullable SpawnPlacementType placementType, @Nullable Heightmap.Types heightmap,
	                                                               SpawnPlacements.SpawnPredicate<ENTITY> predicate,
	                                                               RegisterSpawnPlacementsEvent.Operation operation) {

		@SuppressWarnings("unchecked")
		private void register(RegisterSpawnPlacementsEvent event, EntityType<?> entityType) {
			event.register((EntityType<ENTITY>) entityType, placementType, heightmap, predicate, operation);
		}
	}
}
