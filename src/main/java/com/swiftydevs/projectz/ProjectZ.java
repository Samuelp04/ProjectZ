package com.swiftydevs.projectz;

import com.mojang.logging.LogUtils;
import com.swiftydevs.projectz.Client.ClientEventHandler;
import com.swiftydevs.projectz.Client.GUI.main.MainMenu;
import com.swiftydevs.projectz.Client.Layers.BackpackLayer;
import com.swiftydevs.projectz.Client.commands.AddMoneyCommand;
import com.swiftydevs.projectz.Client.commands.SpawnNpcCommand;
import com.swiftydevs.projectz.Common.Backpack.BackpackInventory;
import com.swiftydevs.projectz.Common.Backpack.IBackpackInventory;
import com.swiftydevs.projectz.Common.Items.backpacks.BackpackItem;
import com.swiftydevs.projectz.Common.entity.InfectedZombie;
import com.swiftydevs.projectz.Common.entity.NpcEntity;
import com.swiftydevs.projectz.Common.entity.renderer.InfectedZombieRenderer;
import com.swiftydevs.projectz.Common.entity.renderer.NpcEntityRenderer;
import com.swiftydevs.projectz.Common.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

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

        modEventBus.addListener(this::enqueueIMC);

        ModContainers.register(modEventBus);

        // Register client-side setup
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::registerAttributes);
        modEventBus.addListener(this::onAddLayers);

        // Register other event handlers
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);

        ModBlockEntities.register(modEventBus);

    }

    public static final Capability<IBackpackInventory> BACKPACK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});


    public void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (String skinType : event.getSkins()) {
            PlayerRenderer playerRenderer = event.getSkin(skinType);
            playerRenderer.addLayer(new BackpackLayer<>(playerRenderer, Minecraft.getInstance().getItemRenderer()));
        }
    }

    private void setup(FMLClientSetupEvent event) {


        // Set rendering layers for blocks
        /*setRenderLayer(ModBlocks.TRASH_CAN.get());
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
        setRenderLayer(ModBlocks.AMMO_BOX.get());*/


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

    private void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypeMessage.Builder builder = new SlotTypeMessage.Builder("back"); // Register "back" slot
        builder.size(1);  // Limit to 1 item in the slot
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, builder::build);
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
            minecraft.setScreen(new MainMenu());
        }
    }
    public static boolean isPlayerInCreative(Player player) {
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            return serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
        }
        return false;
    }

    public static boolean isPlayerInSurvival(Player player) {
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            return serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
        }
        return false;
    }



    private void onRegisterCommands(RegisterCommandsEvent event) {
        // Register commands
        AddMoneyCommand.register(event.getDispatcher());
        SpawnNpcCommand.register(event.getDispatcher());
    }
}
