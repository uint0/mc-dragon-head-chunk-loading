package net.uint0.dragonheadchunkloader;


import net.fabricmc.api.DedicatedServerModInitializer;

public class DragonHeadChunkLoader implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		// TODO: read from configuration file here
		System.out.println("Dragon Head Chunkloading loaded.");
	}
}
