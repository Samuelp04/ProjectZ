package com.swiftydevs.projectz;

import com.mojang.logging.LogUtils;
import com.swiftydevs.projectz.Client.ClientEventHandler;
import com.swiftydevs.projectz.Client.GUI.main.ZMainMenu;
import com.swiftydevs.projectz.Client.commands.AddMoneyCommand;
import com.swiftydevs.projectz.Client.commands.SpawnNpcCommand;
import com.swiftydevs.projectz.Common.entity.InfectedZombie;
import com.swiftydevs.projectz.Common.entity.NpcEntity;
import com.swiftydevs.projectz.Common.entity.renderer.InfectedZombieRenderer;
import com.swiftydevs.projectz.Common.entity.renderer.NpcEntityRenderer;
import com.swiftydevs.projectz.Common.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ProjectZ.MOD_ID)
public class ProjectZ {
    public static final String MOD_ID = "projectz";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ProjectZ() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register mod items, blocks, entities
        ModItems.register();
        ModBlocks.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        // Register client-side setup
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerAttributes);

        // Register other event handlers
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);

        ModBlockEntities.register(modEventBus);

    }



    private void setup(FMLClientSetupEvent event) {
        // Set rendering layers for blocks
        setRenderLayer(ModBlocks.TRASH_CAN.get());
        setRenderLayer(ModBlocks.BARRICADE_1.get());
        setRenderLayer(ModBlocks.BARRICADE_2.get());
        setRenderLayer(ModBlocks.CHAIN_LINK_FENCE.get());
        setRenderLayer(ModBlocks.TRASH_CAN_2.get());
        setRenderLayer(ModBlocks.BIO_HAZARD_BARREL.get());
        setRenderLayer(ModBlocks.BRAIN_JAR.get());
        setRenderLayer(ModBlocks.CONE.get());
        setRenderLayer(ModBlocks.DANGER_SIGN.get());
        setRenderLayer(ModBlocks.GARBAGE_1.get());
        setRenderLayer(ModBlocks.GARBAGE_2.get());
        setRenderLayer(ModBlocks.GRAFFITI_1.get());
        setRenderLayer(ModBlocks.GRAFFITI_2.get());
        setRenderLayer(ModBlocks.NEWS_PAPERS.get());
        setRenderLayer(ModBlocks.SIREN.get());
        setRenderLayer(ModBlocks.SWAY_FLAG.get());
        setRenderLayer(ModBlocks.TORN_FLAG.get());
        setRenderLayer(ModBlocks.WOOD_BOARDS_2.get());
        setRenderLayer(ModBlocks.AMMO_BOX.get());
        setRenderLayer(ModBlocks.BARREL_RED.get());
        setRenderLayer(ModBlocks.BARREL_BLUE.get());
        setRenderLayer(ModBlocks.BARREL_GREEN.get());
        setRenderLayer(ModBlocks.CAMPING_STOOL.get());
        setRenderLayer(ModBlocks.CAR_BATTERY.get());
        setRenderLayer(ModBlocks.CARDBOARD_BOX.get());
        setRenderLayer(ModBlocks.CARDBOARD_BOX2.get());
        setRenderLayer(ModBlocks.CARDBOARD_BOX3.get());
        setRenderLayer(ModBlocks.CASH_REGISTER.get());
        setRenderLayer(ModBlocks.CCTV.get());
        setRenderLayer(ModBlocks.ELECTRIC_BOX1.get());
        setRenderLayer(ModBlocks.ELECTRIC_BOX2.get());
        setRenderLayer(ModBlocks.CONSTRUCTION_LIGHT.get());
        setRenderLayer(ModBlocks.CONSTRUCTION_LIGHT2.get());
        setRenderLayer(ModBlocks.HAZARD_BARRIER.get());
        setRenderLayer(ModBlocks.HAZARDPOLE.get());
        setRenderLayer(ModBlocks.AMMO_BOX.get());


        // Register entity renderer
        EntityRenderers.register(ModEntityTypes.INFECTED_ZOMBIE.get(), InfectedZombieRenderer::new);
        EntityRenderers.register(ModEntityTypes.MEDICAL_TRADER.get(), NpcEntityRenderer::new);

        ModNetworking.register();

        // Set spawn placements
        event.enqueueWork(() -> SpawnPlacements.register(
                ModEntityTypes.INFECTED_ZOMBIE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules
        ));
    }

    private static void setRenderLayer(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent());
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.INFECTED_ZOMBIE.get(), InfectedZombie.createAttributes().build());
        event.put(ModEntityTypes.MEDICAL_TRADER.get(), NpcEntity.createAttributes().build());
    }



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onGuiOpen(ScreenEvent.InitScreenEvent.Post event) {
        if (event.getScreen() instanceof TitleScreen) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new ZMainMenu());
        }
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        // Register commands
        AddMoneyCommand.register(event.getDispatcher());
        SpawnNpcCommand.register(event.getDispatcher());
    }
}
