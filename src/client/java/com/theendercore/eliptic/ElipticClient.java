package com.theendercore.eliptic;

import com.theendercore.eliptic.client.EchoEffectOverlay;
import net.fabricmc.api.ClientModInitializer;

public class ElipticClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EchoEffectOverlay.init();
	}
}