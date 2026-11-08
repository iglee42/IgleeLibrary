package fr.iglee42.igleelib.registries.impl;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Collects payload registrations for the mod event bus. Payloads must override
 * {@link CustomPacketPayload#type()} to return their registered type. Send them
 * using {@link PacketDistributor} or the connection's send methods.
 *
 * <p>Registrations use NeoForge's main-thread handling and required-channel
 * defaults. Use {@link #register(Consumer)} for optional channels, other
 * versions, or network-thread handlers.</p>
 *
 * <p>For example, define the type once and return it from the payload:</p>
 * <pre>{@code
 * static final PacketDeferredRegister PACKETS = HUB.packets("1");
 * static final CustomPacketPayload.Type<ExamplePayload> EXAMPLE =
 *     PACKETS.playToServer("example", EXAMPLE_CODEC, EXAMPLE_HANDLER);
 *
 * record ExamplePayload(int value) implements ExtendedPacket {
 *     @Override
 *     public CustomPacketPayload.Type<ExamplePayload> type() {
 *         return EXAMPLE;
 *     }
 * }
 *
 * // Register payloads before HUB.register(bus).
 * }</pre>
 */
public class PacketDeferredRegister {

	private final String namespace;
	private final String version;
	private final List<Consumer<PayloadRegistrar>> registrations = new ArrayList<>();
	private boolean attached;
	private boolean closed;

	public PacketDeferredRegister(String namespace, String version) {
		this.namespace = Objects.requireNonNull(namespace, "namespace");
		this.version = Objects.requireNonNull(version, "version");
		if (version.isBlank()) throw new IllegalArgumentException("Packet version must not be blank");
	}

	public String version() {
		return version;
	}

	/**
	 * Creates a namespaced type to use both in registration and in the payload.
	 */
	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(String name) {
		return new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(namespace, name));
	}

	/**
	 * Defers a NeoForge registration until the payload registration event.
	 */
	public void register(Consumer<PayloadRegistrar> registration) {
		if (closed) throw new IllegalStateException("Payload registration has already started");
		registrations.add(Objects.requireNonNull(registration, "registration"));
	}

	public <T extends CustomPacketPayload> void playToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.playToClient(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> playToClient(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		playToClient(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void playToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.playToServer(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> playToServer(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		playToServer(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void playBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.playBidirectional(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> playBidirectional(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		playBidirectional(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void configurationToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.configurationToClient(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> configurationToClient(String name, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		configurationToClient(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void configurationToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.configurationToServer(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> configurationToServer(String name, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		configurationToServer(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void configurationBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.configurationBidirectional(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> configurationBidirectional(String name, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		configurationBidirectional(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void commonToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.commonToClient(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> commonToClient(String name, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		commonToClient(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void commonToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.commonToServer(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> commonToServer(String name, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		commonToServer(type, codec, handler);
		return type;
	}

	public <T extends CustomPacketPayload> void commonBidirectional(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		register(registrar -> registrar.commonBidirectional(type, codec, handler));
	}

	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> commonBidirectional(String name, StreamCodec<? super FriendlyByteBuf, T> codec, IPayloadHandler<T> handler) {
		CustomPacketPayload.Type<T> type = type(name);
		commonBidirectional(type, codec, handler);
		return type;
	}

	public void register(IEventBus bus) {
		if (attached) throw new IllegalStateException("Packet register is already attached to an event bus");
		bus.addListener(this::onRegisterPayloads);
		attached = true;
	}

	private void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
		closed = true;
		PayloadRegistrar registrar = event.registrar(version);
		registrations.forEach(registration -> registration.accept(registrar));
	}
}
