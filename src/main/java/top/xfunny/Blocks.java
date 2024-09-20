package top.xfunny;

import org.mtr.mapping.holder.Block;
import org.mtr.mapping.holder.Identifier;
import org.mtr.mapping.registry.BlockRegistryObject;
import org.mtr.mod.CreativeModeTabs;

import top.xfunny.Block.OtisSeries1Button;
import top.xfunny.Block.TestLiftButtons;
import top.xfunny.Block.TestLiftButtonsWithoutScreen;
import top.xfunny.Block.TestLiftHallLanterns;


public class Blocks {


    public static final BlockRegistryObject TEST_LIFT_BUTTONS;
    public static final BlockRegistryObject TEST_LIFT_HALL_LANTERNS;
    public static final BlockRegistryObject TEST_LIFT_BUTTONS_WITHOUT_SCREEN;
    public static final BlockRegistryObject OTIS_SERIES_1_BUTTON_1;
    public static final BlockRegistryObject OTIS_SERIES_1_BUTTON_2;

    static {

        TEST_LIFT_BUTTONS = Init.REGISTRY.registerBlockWithBlockItem(new Identifier(Init.MOD_ID, "test_lift_buttons"), () -> new Block(new TestLiftButtons()), CreativeModeTabs.ESCALATORS_LIFTS);
        TEST_LIFT_HALL_LANTERNS = Init.REGISTRY.registerBlockWithBlockItem(new Identifier(Init.MOD_ID, "test_lift_hall_lanterns"), () -> new Block(new TestLiftHallLanterns()), CreativeModeTabs.ESCALATORS_LIFTS);
        TEST_LIFT_BUTTONS_WITHOUT_SCREEN = Init.REGISTRY.registerBlockWithBlockItem(new Identifier(Init.MOD_ID, "test_lift_buttons_without_screen"), () -> new Block(new TestLiftButtonsWithoutScreen()), CreativeModeTabs.ESCALATORS_LIFTS);
        OTIS_SERIES_1_BUTTON_1 = Init.REGISTRY.registerBlockWithBlockItem(new Identifier(Init.MOD_ID, "otis_series_1_button_1"), () -> new Block(new OtisSeries1Button()), CreativeModeTabs.ESCALATORS_LIFTS);
        OTIS_SERIES_1_BUTTON_2 = Init.REGISTRY.registerBlockWithBlockItem(new Identifier(Init.MOD_ID, "otis_series_1_button_2"), () -> new Block(new OtisSeries1Button()), CreativeModeTabs.ESCALATORS_LIFTS);

    }


    public static void init() {
		Init.LOGGER.info("正在注册方块");
	}
}
