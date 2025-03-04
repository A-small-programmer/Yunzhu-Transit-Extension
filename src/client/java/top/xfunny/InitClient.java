package top.xfunny;


import org.mtr.mapping.holder.Identifier;
import org.mtr.mapping.holder.RenderLayer;
import org.mtr.mapping.registry.RegistryClient;
import org.mtr.mod.client.MinecraftClientData;
import org.mtr.mod.item.ItemBlockClickingBase;
import top.xfunny.render.*;


public final class InitClient {
    public static final RegistryClient REGISTRY_CLIENT = new RegistryClient(Init.REGISTRY);
    private static long lastMillis = 0;
    private static long gameMillis = 0;


    public static void init() {
        REGISTRY_CLIENT.registerBlockRenderType(RenderLayer.getCutout(), Blocks.SCHINDLER_QKS9_DOOR_1);
        REGISTRY_CLIENT.registerBlockRenderType(RenderLayer.getCutout(), Blocks.MITSUBISHI_NEXWAY_DOOR_1);


        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.TEST_LIFT_BUTTONS, RenderTestLiftButtons4::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.TEST_LIFT_HALL_LANTERNS, dispatcher -> new RenderTestLiftHallLanterns(dispatcher, true));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.TEST_LIFT_BUTTONS_WITHOUT_SCREEN, RenderTestLiftButtonsWithoutScreen::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.TEST_LIFT_PANEL, dispatcher -> new RenderTestLiftPanel(dispatcher, true));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.TEST_LIFT_DESTINATION_DISPATCH_TERMINAL, dispatcher -> new RenderTestLiftDestinationDispatchTerminal(dispatcher, true));


        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_BUTTON_1, RenderMitsubishiNexWayButton1::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_BUTTON_1_WITHOUT_SCREEN, RenderMitsubishiNexWayButton1WithoutScreen::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_BUTTON_2, RenderMitsubishiNexWayButton2::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_BUTTON_2_WITHOUT_SCREEN, RenderMitsubishiNexWayButton2WithoutScreen::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_BUTTON_3, RenderMitsubishiNexWayButton3::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_BUTTON_3_WITHOUT_SCREEN, RenderMitsubishiNexWayButton3WithoutScreen::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_SCREEN_1_EVEN, dispatcher -> new RenderMitsubishiNexWayScreen1<>(dispatcher, false));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_SCREEN_1_ODD, dispatcher -> new RenderMitsubishiNexWayScreen1<>(dispatcher, true));


        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.OTIS_SERIES_1_BUTTON_1, RenderOtisSeries1Button::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.OTIS_SERIES_1_BUTTON_2, RenderOtisSeries1Button::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.OTIS_SERIES_1_SCREEN_1, dispatcher -> new RenderOtisSeries1Screen(dispatcher, true));


        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_D2BUTTON, RenderSchindlerDSeriesD2Button::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_1_ODD, dispatcher -> new RenderSchindlerDSeriesScreen1(dispatcher, true));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_1_EVEN, dispatcher -> new RenderSchindlerDSeriesScreen1(dispatcher, false));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_2_GREEN_ODD, dispatcher -> new RenderSchindlerDSeriesScreen2<>(dispatcher, true, RenderSchindlerDSeriesScreen2.renderSchindlerDSeriesScreen2Color.GREEN));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_2_GREEN_EVEN, dispatcher -> new RenderSchindlerDSeriesScreen2<>(dispatcher, false, RenderSchindlerDSeriesScreen2.renderSchindlerDSeriesScreen2Color.GREEN));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_2_BLUE_ODD, dispatcher -> new RenderSchindlerDSeriesScreen2<>(dispatcher, true, RenderSchindlerDSeriesScreen2.renderSchindlerDSeriesScreen2Color.BLUE));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_2_BLUE_EVEN, dispatcher -> new RenderSchindlerDSeriesScreen2<>(dispatcher, false, RenderSchindlerDSeriesScreen2.renderSchindlerDSeriesScreen2Color.BLUE));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_2_RED_ODD, dispatcher -> new RenderSchindlerDSeriesScreen2<>(dispatcher, true, RenderSchindlerDSeriesScreen2.renderSchindlerDSeriesScreen2Color.RED));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_D_SERIES_SCREEN_2_RED_EVEN, dispatcher -> new RenderSchindlerDSeriesScreen2<>(dispatcher, false, RenderSchindlerDSeriesScreen2.renderSchindlerDSeriesScreen2Color.RED));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_M_SERIES_BUTTON, RenderSchindlerMSeriesButton::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_M_SERIES_TOUCH_BUTTON, RenderSchindlerMSeriesTouchButton::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_M_SERIES_SCREEN_1, dispatcher -> new RenderSchindlerMSeriesScreen1(dispatcher, true));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_M_SERIES_SCREEN_2_ODD, dispatcher -> new RenderSchindlerMSeriesScreen2<>(dispatcher, true));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_M_SERIES_SCREEN_2_EVEN, dispatcher -> new RenderSchindlerMSeriesScreen2<>(dispatcher, false));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_S_SERIES_GREY_BUTTON, RenderSchindlerSSeriesGreyButton::new);
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_Z_LINE_3_KEYPAD_1, dispatcher -> new RenderSchindlerZLine3Keypad1(dispatcher, true));


        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.HITACHI_B85_BUTTON_1, RenderHitachiB85Button1::new);


        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.MITSUBISHI_NEXWAY_DOOR_1, dispatcher -> new RenderLiftDoor<>(dispatcher, 5));
        REGISTRY_CLIENT.registerBlockEntityRenderer(BlockEntityTypes.SCHINDLER_QKS9_DOOR_1, dispatcher -> new RenderLiftDoor<>(dispatcher, 3));


        REGISTRY_CLIENT.registerItemModelPredicate(Items.YTE_LIFT_BUTTONS_LINK_CONNECTOR, new Identifier(org.mtr.mod.Init.MOD_ID, "selected"), checkItemPredicateTag());
        REGISTRY_CLIENT.registerItemModelPredicate(Items.YTE_LIFT_BUTTONS_LINK_REMOVER, new Identifier(org.mtr.mod.Init.MOD_ID, "selected"), checkItemPredicateTag());
        REGISTRY_CLIENT.registerItemModelPredicate(Items.YTE_GROUP_LIFT_BUTTONS_LINK_CONNECTOR, new Identifier(org.mtr.mod.Init.MOD_ID, "selected"), checkItemPredicateTag());
        REGISTRY_CLIENT.registerItemModelPredicate(Items.YTE_GROUP_LIFT_BUTTONS_LINK_REMOVER, new Identifier(org.mtr.mod.Init.MOD_ID, "selected"), checkItemPredicateTag());

        REGISTRY_CLIENT.setupPackets(new Identifier(Init.MOD_ID, "packet"));

        REGISTRY_CLIENT.eventRegistryClient.registerClientJoin(() -> {
            MinecraftClientData.reset();
            TextureCache.instance = new TextureCache();
            lastMillis = System.currentTimeMillis();
            gameMillis = 0;

            TextureCache.instance.reload();

        });
        REGISTRY_CLIENT.init();
    }


    private static RegistryClient.ModelPredicateProvider checkItemPredicateTag() {
        return (itemStack, clientWorld, livingEntity) -> itemStack.getOrCreateTag().contains(ItemBlockClickingBase.TAG_POS) ? 1 : 0;
    }

    public static float getGameTick() {
        return (float) gameMillis / 50.0F;
    }
}