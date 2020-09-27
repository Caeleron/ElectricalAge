package mods.eln;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.eln.block.ArcClayBlock;
import mods.eln.block.ArcClayItemBlock;
import mods.eln.block.ArcMetalBlock;
import mods.eln.block.ArcMetalItemBlock;
import mods.eln.cable.CableRenderDescriptor;
import mods.eln.client.ClientKeyHandler;
import mods.eln.client.SoundLoader;
import mods.eln.crafting.CraftingRegistry;
import mods.eln.entity.ReplicatorEntity;
import mods.eln.entity.ReplicatorPopProcess;
import mods.eln.generic.GenericCreativeTab;
import mods.eln.generic.GenericItemUsingDamageDescriptor;
import mods.eln.generic.GenericItemUsingDamageDescriptorWithComment;
import mods.eln.generic.SharedItem;
import mods.eln.ghost.GhostBlock;
import mods.eln.ghost.GhostManager;
import mods.eln.ghost.GhostManagerNbt;
import mods.eln.item.CopperCableDescriptor;
import mods.eln.item.GraphiteDescriptor;
import mods.eln.item.MiningPipeDescriptor;
import mods.eln.item.TreeResin;
import mods.eln.item.electricalinterface.ItemEnergyInventoryProcess;
import mods.eln.item.electricalitem.OreScannerTasks;
import mods.eln.item.electricalitem.PortableOreScannerItem.RenderStorage.OreScannerConfigElement;
import mods.eln.misc.FunctionTable;
import mods.eln.misc.IConfigSharing;
import mods.eln.misc.LiveDataManager;
import mods.eln.misc.Obj3DFolder;
import mods.eln.misc.RecipesList;
import mods.eln.misc.Utils;
import mods.eln.misc.Version;
import mods.eln.misc.WindProcess;
import mods.eln.node.NodeBlockEntity;
import mods.eln.node.NodeManager;
import mods.eln.node.NodeManagerNbt;
import mods.eln.node.NodeServer;
import mods.eln.node.six.SixNode;
import mods.eln.node.six.SixNodeBlock;
import mods.eln.node.six.SixNodeCacheStd;
import mods.eln.node.six.SixNodeEntity;
import mods.eln.node.six.SixNodeItem;
import mods.eln.node.transparent.TransparentNode;
import mods.eln.node.transparent.TransparentNodeBlock;
import mods.eln.node.transparent.TransparentNodeEntity;
import mods.eln.node.transparent.TransparentNodeEntityWithFluid;
import mods.eln.node.transparent.TransparentNodeItem;
import mods.eln.ore.OreBlock;
import mods.eln.ore.OreDescriptor;
import mods.eln.ore.OreItem;
import mods.eln.packets.GhostNodeWailaRequestPacket;
import mods.eln.packets.GhostNodeWailaRequestPacketHandler;
import mods.eln.packets.GhostNodeWailaResponsePacket;
import mods.eln.packets.GhostNodeWailaResponsePacketHandler;
import mods.eln.packets.SixNodeWailaRequestPacket;
import mods.eln.packets.SixNodeWailaRequestPacketHandler;
import mods.eln.packets.SixNodeWailaResponsePacket;
import mods.eln.packets.SixNodeWailaResponsePacketHandler;
import mods.eln.packets.TransparentNodeRequestPacket;
import mods.eln.packets.TransparentNodeRequestPacketHandler;
import mods.eln.packets.TransparentNodeResponsePacket;
import mods.eln.packets.TransparentNodeResponsePacketHandler;
import mods.eln.registry.BlockRegistry;
import mods.eln.registry.EntityRegistry;
import mods.eln.registry.ItemRegistry;
import mods.eln.registry.SixNodeRegistry;
import mods.eln.registry.TransparentNodeRegistry;
import mods.eln.server.ConsoleListener;
import mods.eln.server.DelayedBlockRemove;
import mods.eln.server.DelayedTaskManager;
import mods.eln.server.OreRegenerate;
import mods.eln.server.PlayerManager;
import mods.eln.server.SaveConfig;
import mods.eln.server.ServerEventListener;
import mods.eln.sim.Simulator;
import mods.eln.sim.ThermalLoadInitializer;
import mods.eln.sim.mna.component.Resistor;
import mods.eln.sim.nbt.NbtElectricalLoad;
import mods.eln.simplenode.computerprobe.ComputerProbeBlock;
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherBlock;
import mods.eln.sixnode.PortableNaNDescriptor;
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor;
import mods.eln.sixnode.electricaldatalogger.DataLogsPrintDescriptor;
import mods.eln.sixnode.lampsocket.LightBlock;
import mods.eln.sixnode.lampsocket.LightBlockEntity;
import mods.eln.sixnode.lampsupply.LampSupplyElement;
import mods.eln.sixnode.modbusrtu.ModbusTcpServer;
import mods.eln.sixnode.tutorialsign.TutorialSignElement;
import mods.eln.sixnode.wirelesssignal.IWirelessSignalSpot;
import mods.eln.sixnode.wirelesssignal.tx.WirelessSignalTxElement;
import mods.eln.transparentnode.computercraftio.PeripheralHandler;
import mods.eln.transparentnode.electricalfurnace.ElectricalFurnaceDescriptor;
import mods.eln.transparentnode.teleporter.TeleporterElement;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import static mods.eln.i18n.I18N.TR;
import static mods.eln.i18n.I18N.TR_NAME;
import static mods.eln.i18n.I18N.Type;
import static mods.eln.i18n.I18N.tr;

@Mod(modid = Eln.MODID, name = Eln.NAME, version = "@VERSION@")
public class Eln {
    // Mod information (override from 'mcmod.info' file)
    public final static String MODID = "Eln";
    public final static String NAME = "Electrical Age";
    public final static String MODDESC = "Electricity in your base!";
    public final static String URL = "https://eln.ja13.org";
    public final static String UPDATE_URL = "https://github.com/jrddunbr/ElectricalAge/releases";
    public final static String SRC_URL = "https://github.com/jrddunbr/ElectricalAge";
    public final static String[] AUTHORS = {"Dolu1990", "lambdaShade", "cm0x4D", "metc", "Baughn", "jrddunbr", "Grissess", "OmegaHaxors"};
    public static final String channelName = "miaouMod";

    // The instance of your mod that Forge uses.
    @Instance("Eln")
    public static Eln instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "mods.eln.client.ClientProxy", serverSide = "mods.eln.CommonProxy")
    public static CommonProxy proxy;

    public static final double solarPanelBasePower = 65.0;
    public static ArrayList<IConfigSharing> configShared = new ArrayList<>();
    public static SimpleNetworkWrapper elnNetwork;
    public static final byte packetPlayerKey = 14;
    public static final byte packetNodeSingleSerialized = 15;
    public static final byte packetPublishForNode = 16;
    public static final byte packetOpenLocalGui = 17;
    public static final byte packetForClientNode = 18;
    public static final byte packetPlaySound = 19;
    public static final byte packetDestroyUuid = 20;
    public static final byte packetClientToServerConnection = 21;
    public static final byte packetServerToClientInfo = 22;
    public static PacketHandler packetHandler;
    public static NodeServer nodeServer;
    public static LiveDataManager clientLiveDataManager;
    public static ClientKeyHandler clientKeyHandler;
    public static SaveConfig saveConfig;
    public static GhostManager ghostManager;
    public static GhostManagerNbt ghostManagerNbt;
    public static NodeManager nodeManager;
    public static PlayerManager playerManager;
    public static ModbusTcpServer modbusServer;
    public static NodeManagerNbt nodeManagerNbt;
    public static Simulator simulator = null;
    public static DelayedTaskManager delayedTask;
    public static ItemEnergyInventoryProcess itemEnergyInventoryProcess;
    public static CreativeTabs creativeTab;
    public static double fuelGeneratorTankCapacity = 20 * 60;
    public static GenericItemUsingDamageDescriptor multiMeterElement,
        thermometerElement, allMeterElement;
    public static GenericItemUsingDamageDescriptor configCopyToolElement;
    public static TreeResin treeResin;
    public static MiningPipeDescriptor miningPipeDescriptor;
    public static DataLogsPrintDescriptor dataLogsPrintDescriptor;
    public static GenericItemUsingDamageDescriptorWithComment tinIngot, copperIngot,
        silverIngot, plumbIngot, tungstenIngot;
    public static GenericItemUsingDamageDescriptorWithComment dustTin,
        dustCopper, dustSilver;
    public static final HashMap<String, ItemStack> dictionnaryOreFromMod = new HashMap<>();
    public static int replicatorRegistrationId = -1;
    public static CableRenderDescriptor stdCableRenderSignal;
    public static CableRenderDescriptor stdCableRenderSignalBus;
    public static CableRenderDescriptor stdCableRender50V;
    public static CableRenderDescriptor stdCableRender200V;
    public static CableRenderDescriptor stdCableRender800V;
    public static CableRenderDescriptor stdCableRender3200V;
    public static CableRenderDescriptor stdCableRenderCreative;
    public static final double gateOutputCurrent = 0.100;
    public static final double SVU = 50, SVII = gateOutputCurrent / 50,
        SVUinv = 1.0 / SVU;
    public static final double LVU = 50;
    public static final double MVU = 200;
    public static final double HVU = 800;
    public static final double VVU = 3200;
    public static final double SVP = gateOutputCurrent * SVU;
    public static final double cableHeatingTime = 30;
    public static final double cableWarmLimit = 130;
    public static final double cableThermalConductionTao = 0.5;
    public static final ThermalLoadInitializer cableThermalLoadInitializer = new ThermalLoadInitializer(
        cableWarmLimit, -100, cableHeatingTime, cableThermalConductionTao);
    public static final ThermalLoadInitializer sixNodeThermalLoadInitializer = new ThermalLoadInitializer(
        cableWarmLimit, -100, cableHeatingTime, 1000);
    public static int wirelessTxRange = 32;
    public static FunctionTable batteryVoltageFunctionTable;
    public static ArrayList<ItemStack> furnaceList = new ArrayList<>();
    public static RecipesList maceratorRecipes = new RecipesList();
    public static RecipesList compressorRecipes = new RecipesList();
    public static RecipesList plateMachineRecipes = new RecipesList();
    public static RecipesList arcFurnaceRecipes = new RecipesList();
    public static RecipesList magnetiserRecipes = new RecipesList();
    public static  double incandescentLampLife;
    public static  double economicLampLife;
    public static  double carbonLampLife;
    public static  double ledLampLife;
    public static boolean ledLampInfiniteLife = false;
    public static OreDescriptor oreCopper, oreSilver;
    public static Item swordCopper, hoeCopper, shovelCopper, pickaxeCopper, axeCopper;
    public static ItemArmor helmetCopper, plateCopper, legsCopper, bootsCopper;
    public static ItemArmor helmetECoal, plateECoal, legsECoal, bootsECoal;
    public static SharedItem sharedItem;
    public static SharedItem sharedItemStackOne;
    public static ItemStack wrenchItemStack;
    public static SixNodeBlock sixNodeBlock;
    public static TransparentNodeBlock transparentNodeBlock;
    public static OreBlock oreBlock;
    public static GhostBlock ghostBlock;
    public static LightBlock lightBlock;
    public static ArcClayBlock arcClayBlock;
    public static ArcMetalBlock arcMetalBlock;
    public static SixNodeItem sixNodeItem;
    public static TransparentNodeItem transparentNodeItem;
    public static OreItem oreItem;
    public static String analyticsURL = "";
    public static boolean analyticsPlayerUUIDOptIn = false;
    public static WindProcess wind;
    public static ServerEventListener serverEventListener;
    public static EnergyConverterElnToOtherBlock elnToOtherBlockLvu;
    public static EnergyConverterElnToOtherBlock elnToOtherBlockMvu;
    public static EnergyConverterElnToOtherBlock elnToOtherBlockHvu;
    public static double electricalFrequency, thermalFrequency;
    public static int electricalInterSystemOverSampling;
    public static CopperCableDescriptor copperCableDescriptor;
    public static GraphiteDescriptor GraphiteDescriptor;
    public static ElectricalCableDescriptor creativeCableDescriptor;
    public static ElectricalCableDescriptor veryHighVoltageCableDescriptor;
    public static ElectricalCableDescriptor highVoltageCableDescriptor;
    public static ElectricalCableDescriptor signalCableDescriptor;
    public static ElectricalCableDescriptor lowVoltageCableDescriptor;
    public static ElectricalCableDescriptor batteryCableDescriptor;
    public static ElectricalCableDescriptor meduimVoltageCableDescriptor;
    public static ElectricalCableDescriptor signalBusCableDescriptor;
    public static PortableNaNDescriptor portableNaNDescriptor = null;
    public static CableRenderDescriptor stdPortableNaN = null;
    public static OreRegenerate oreRegenerate;
    public static final Obj3DFolder obj = new Obj3DFolder();
    public static boolean oredictTungsten, oredictChips;
    public static boolean genCopper, genLead, genTungsten, genCinnabar;
    public static String dictTungstenOre, dictTungstenDust, dictTungstenIngot;
    public static String dictCheapChip, dictAdvancedChip;
    public static final ArrayList<OreScannerConfigElement> oreScannerConfig = new ArrayList<>();
    public static boolean modbusEnable = false;
    public static int modbusPort;
    public static double xRayScannerRange;
    public static boolean addOtherModOreToXRay;
    public static boolean replicatorPop;
    public static boolean xRayScannerCanBeCrafted = true;
    public static boolean forceOreRegen;
    public static boolean explosionEnable;
    public static boolean debugEnabled = false;  // Read from configuration file. Default is `false`.
    public static boolean versionCheckEnabled = true; // Read from configuration file. Default is `true`.
    public static boolean analyticsEnabled = true; // Read from configuration file. Default is `true`.
    public static String playerUUID = null; // Read from configuration file. Default is `null`.
    public static double heatTurbinePowerFactor = 1;
    public static double solarPanelPowerFactor = 1;
    public static double windTurbinePowerFactor = 1;
    public static double waterTurbinePowerFactor = 1;
    public static double fuelGeneratorPowerFactor = 1;
    public static double fuelHeatFurnacePowerFactor = 1;
    public static int autominerRange = 10;
    public static boolean killMonstersAroundLamps;
    public static int killMonstersAroundLampsRange;
    public static int maxReplicators = 100;
    public static double stdBatteryHalfLife = 2 * Utils.minecraftDay;
    public static double batteryCapacityFactor = 1.;
    public static boolean wailaEasyMode = false;
    public static double shaftEnergyFactor = 0.05;
    public static double fuelHeatValueFactor = 0.0000675;
    public static int plateConversionRatio;
    public static boolean noSymbols = false;
    public static boolean noVoltageBackground = false;
    public static double maxSoundDistance = 16;
    public static double cablePowerFactor;
    public static boolean allowSwingingLamps = true;
    public static boolean enableFestivities = true;
    public static FMLEventChannel eventChannel;
    public static boolean ComputerProbeEnable;
    public static boolean ElnToOtherEnergyConverterEnable;
    public static HashSet<String> oreNames = new HashSet<>();
    public static ElectricalFurnaceDescriptor electricalFurnace;
    public static ComputerProbeBlock computerProbeBlock;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        elnNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("electrical-age");
        elnNetwork.registerMessage(TransparentNodeRequestPacketHandler.class, TransparentNodeRequestPacket.class, 1, Side.SERVER);
        elnNetwork.registerMessage(TransparentNodeResponsePacketHandler.class, TransparentNodeResponsePacket.class, 2, Side.CLIENT);
        elnNetwork.registerMessage(GhostNodeWailaRequestPacketHandler.class, GhostNodeWailaRequestPacket.class, 3, Side.SERVER);
        elnNetwork.registerMessage(GhostNodeWailaResponsePacketHandler.class, GhostNodeWailaResponsePacket.class, 4, Side.CLIENT);
        elnNetwork.registerMessage(SixNodeWailaRequestPacketHandler.class, SixNodeWailaRequestPacket.class, 5, Side.SERVER);
        elnNetwork.registerMessage(SixNodeWailaResponsePacketHandler.class, SixNodeWailaResponsePacket.class, 6, Side.CLIENT);

        // Update ModInfo by code
        ModMetadata meta = event.getModMetadata();
        meta.modId = MODID;
        meta.version = Version.getVersionName();
        meta.name = NAME;
        meta.description = tr("mod.meta.desc");
        meta.url = URL;
        meta.updateUrl = UPDATE_URL;
        meta.authorList = Arrays.asList(AUTHORS);
        meta.autogenerated = false; // Force to update from code

        Utils.println(Version.print());

        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT)
            //noinspection InstantiationOfUtilityClass
            MinecraftForge.EVENT_BUS.register(new SoundLoader());

        ConfigHelper.Companion.loadConfiguration(event);

        eventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);

        simulator = new Simulator(0.05, 1 / electricalFrequency, electricalInterSystemOverSampling, 1 / thermalFrequency);
        nodeManager = new NodeManager("caca");
        ghostManager = new GhostManager("caca2");
        delayedTask = new DelayedTaskManager();
        playerManager = new PlayerManager();
        oreRegenerate = new OreRegenerate();
        nodeServer = new NodeServer();
        clientLiveDataManager = new LiveDataManager();
        packetHandler = new PacketHandler();
        instance = this;

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        Item itemCreativeTab = new Item()
            .setUnlocalizedName("eln:elncreativetab")
            .setTextureName("eln:elncreativetab");
        GameRegistry.registerItem(itemCreativeTab, "eln.itemCreativeTab");
        creativeTab = new GenericCreativeTab("Eln", itemCreativeTab);
        oreBlock = (OreBlock) new OreBlock().setCreativeTab(creativeTab).setBlockName("OreEln");
        arcClayBlock = new ArcClayBlock();
        arcMetalBlock = new ArcMetalBlock();
        sharedItem = (SharedItem) new SharedItem()
            .setCreativeTab(creativeTab).setMaxStackSize(64)
            .setUnlocalizedName("sharedItem");
        sharedItemStackOne = (SharedItem) new SharedItem()
            .setCreativeTab(creativeTab).setMaxStackSize(1)
            .setUnlocalizedName("sharedItemStackOne");
        transparentNodeBlock = (TransparentNodeBlock) new TransparentNodeBlock(
            Material.iron,
            TransparentNodeEntity.class)
            .setCreativeTab(creativeTab)
            .setBlockTextureName("iron_block");
        sixNodeBlock = (SixNodeBlock) new SixNodeBlock(
            Material.plants, SixNodeEntity.class)
            .setCreativeTab(creativeTab)
            .setBlockTextureName("iron_block");
        ghostBlock = (GhostBlock) new GhostBlock().setBlockTextureName("iron_block");
        lightBlock = new LightBlock();
        obj.loadAllElnModels();
        GameRegistry.registerItem(sharedItem, "Eln.sharedItem");
        GameRegistry.registerItem(sharedItemStackOne, "Eln.sharedItemStackOne");
        GameRegistry.registerBlock(ghostBlock, "Eln.ghostBlock");
        GameRegistry.registerBlock(lightBlock, "Eln.lightBlock");
        GameRegistry.registerBlock(sixNodeBlock, SixNodeItem.class, "Eln.SixNode");
        GameRegistry.registerBlock(transparentNodeBlock, TransparentNodeItem.class, "Eln.TransparentNode");
        GameRegistry.registerBlock(oreBlock, OreItem.class, "Eln.Ore");
        GameRegistry.registerBlock(arcClayBlock, ArcClayItemBlock.class, "Eln.arc_clay_block");
        GameRegistry.registerBlock(arcMetalBlock, ArcMetalItemBlock.class, "Eln.arc_metal_block");
        TileEntity.addMapping(TransparentNodeEntity.class, "TransparentNodeEntity");
        TileEntity.addMapping(TransparentNodeEntityWithFluid.class, "TransparentNodeEntityWF");
        // TileEntity.addMapping(TransparentNodeEntityWithSiededInv.class, "TransparentNodeEntityWSI");
        TileEntity.addMapping(SixNodeEntity.class, "SixNodeEntity");
        TileEntity.addMapping(LightBlockEntity.class, "LightBlockEntity");
        NodeManager.registerUuid(sixNodeBlock.getNodeUuid(), SixNode.class);
        NodeManager.registerUuid(transparentNodeBlock.getNodeUuid(), TransparentNode.class);
        sixNodeItem = (SixNodeItem) Item.getItemFromBlock(sixNodeBlock);
        transparentNodeItem = (TransparentNodeItem) Item.getItemFromBlock(transparentNodeBlock);
        oreItem = (OreItem) Item.getItemFromBlock(oreBlock);
        SixNode.sixNodeCacheList.add(new SixNodeCacheStd());
        BlockRegistry.Companion.registerBlocks();
        SixNodeRegistry.Companion.register();
        TransparentNodeRegistry.Companion.register();
        ItemRegistry.Companion.register();
        OreDictionary.registerOre("blockAluminum", arcClayBlock);
        OreDictionary.registerOre("blockSteel", arcMetalBlock);
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent event) {
        Other.check();
        if (Other.ccLoaded) {
            PeripheralHandler.register();
        }
        //CraftingRegistry.Companion.recipeMaceratorModOres();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        Collections.addAll(oreNames, OreDictionary.getOreNames());
        EntityRegistry.Companion.registerEntities();
        CraftingRegistry.Companion.registerCrafting();
        proxy.registerRenderers();
        TR("itemGroup.Eln");
        CraftingRegistry.Companion.checkRecipe();
        FMLInterModComms.sendMessage("Waila", "register", "mods.eln.integration.waila.WailaIntegration.callbackRegister");
        Utils.println("Electrical age init done");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        serverEventListener = new ServerEventListener();
    }

    @EventHandler
    /* Remember to use the right event! */
    public void onServerStopped(FMLServerStoppedEvent ev) {
        TutorialSignElement.resetBalise();
        if (modbusServer != null) {
            modbusServer.destroy();
            modbusServer = null;
        }
        LightBlockEntity.observers.clear();
        NodeBlockEntity.clientList.clear();
        TeleporterElement.teleporterList.clear();
        IWirelessSignalSpot.spots.clear();
        playerManager.clear();
        clientLiveDataManager.stop();
        nodeManager.clear();
        ghostManager.clear();
        saveConfig = null;
        modbusServer = null;
        oreRegenerate.clear();
        delayedTask.clear();
        DelayedBlockRemove.clear();
        serverEventListener.clear();
        nodeServer.stop();
        simulator.stop();
        //tileEntityDestructor.clear();
        LampSupplyElement.channelMap.clear();
        WirelessSignalTxElement.channelMap.clear();
    }

    @EventHandler
    public void onServerStart(FMLServerAboutToStartEvent ev) {
        modbusServer = new ModbusTcpServer(modbusPort);
        TeleporterElement.teleporterList.clear();
        LightBlockEntity.observers.clear();
        WirelessSignalTxElement.channelMap.clear();
        LampSupplyElement.channelMap.clear();
        playerManager.clear();
        clientLiveDataManager.start();
        simulator.init();
        simulator.addSlowProcess(wind = new WindProcess());
        if (replicatorPop)
            simulator.addSlowProcess(new ReplicatorPopProcess());
        simulator.addSlowProcess(itemEnergyInventoryProcess = new ItemEnergyInventoryProcess());
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent ev) {
        {
            MinecraftServer server = FMLCommonHandler.instance()
                .getMinecraftServerInstance();
            WorldServer worldServer = server.worldServers[0];
            ghostManagerNbt = (GhostManagerNbt) worldServer.mapStorage.loadData(
                GhostManagerNbt.class, "GhostManager");
            if (ghostManagerNbt == null) {
                ghostManagerNbt = new GhostManagerNbt("GhostManager");
                worldServer.mapStorage.setData("GhostManager", ghostManagerNbt);
            }
            saveConfig = (SaveConfig) worldServer.mapStorage.loadData(
                SaveConfig.class, "SaveConfig");
            if (saveConfig == null) {
                saveConfig = new SaveConfig("SaveConfig");
                worldServer.mapStorage.setData("SaveConfig", saveConfig);
            }
            nodeManagerNbt = (NodeManagerNbt) worldServer.mapStorage.loadData(
                NodeManagerNbt.class, "NodeManager");
            if (nodeManagerNbt == null) {
                nodeManagerNbt = new NodeManagerNbt("NodeManager");
                worldServer.mapStorage.setData("NodeManager", nodeManagerNbt);
            }
            nodeServer.init();
        }

        {
            MinecraftServer s = MinecraftServer.getServer();
            ICommandManager command = s.getCommandManager();
            ServerCommandManager manager = (ServerCommandManager) command;
            manager.registerCommand(new ConsoleListener());
        }
        OreScannerTasks.Companion.regenOreScannerFactors();
    }

    public static double LVP() {
        return 1000 * cablePowerFactor;
    }
    public static double MVP() {
        return 2000 * cablePowerFactor;
    }
    public static double HVP() {
        return 5000 * cablePowerFactor;
    }
    public static double VVP() {
        return 15000 * cablePowerFactor;
    }

    public static double getSmallRs() {
        return lowVoltageCableDescriptor.electricalRs;
    }
    public static void applySmallRs(NbtElectricalLoad aLoad) {
        lowVoltageCableDescriptor.applyTo(aLoad);
    }
    public static void applySmallRs(Resistor r) {
        lowVoltageCableDescriptor.applyTo(r);
    }

    public boolean isDevelopmentRun() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }
}
