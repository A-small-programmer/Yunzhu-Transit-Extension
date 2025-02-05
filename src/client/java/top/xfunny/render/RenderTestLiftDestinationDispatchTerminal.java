package top.xfunny.render;

import org.mtr.mapping.holder.*;
import org.mtr.mapping.mapper.BlockEntityRenderer;
import org.mtr.mapping.mapper.DirectionHelper;
import org.mtr.mapping.mapper.GraphicsHolder;
import org.mtr.mapping.mapper.PlayerHelper;
import org.mtr.mod.block.IBlock;
import org.mtr.mod.data.IGui;
import org.mtr.mod.render.StoredMatrixTransformations;
import top.xfunny.Init;
import top.xfunny.block.OtisSeries1Button;
import top.xfunny.block.TestLiftDestinationDispatchTerminal;
import top.xfunny.block.base.LiftButtonsBase;
import top.xfunny.item.YteGroupLiftButtonsLinker;
import top.xfunny.item.YteLiftButtonsLinker;
import top.xfunny.view.ButtonView;
import top.xfunny.view.LayoutSize;
import top.xfunny.view.view_group.LinearLayout;

public class RenderTestLiftDestinationDispatchTerminal extends BlockEntityRenderer<TestLiftDestinationDispatchTerminal.BlockEntity> implements DirectionHelper, IGui, IBlock {
    private final boolean isOdd;
    private static final int HOVER_COLOR = 0xFFFFCC66;

    public RenderTestLiftDestinationDispatchTerminal(Argument dispatcher, boolean isOdd) {
        super(dispatcher);
        this.isOdd = isOdd;
    }

    @Override
    public void render(TestLiftDestinationDispatchTerminal.BlockEntity blockEntity, float tickDelta, GraphicsHolder graphicsHolder1, int light, int overlay) {
        final World world = blockEntity.getWorld2();
        final String screenId = blockEntity.getScreenId();

        if (world == null) {
            return;
        }

        final ClientPlayerEntity clientPlayerEntity = MinecraftClient.getInstance().getPlayerMapped();
        if (clientPlayerEntity == null) {
            return;
        }

        final boolean holdingLinker = PlayerHelper.isHolding(PlayerEntity.cast(clientPlayerEntity), item -> item.data instanceof YteLiftButtonsLinker || item.data instanceof YteGroupLiftButtonsLinker);
        final BlockPos blockPos = blockEntity.getPos2();
        final BlockState blockState = world.getBlockState(blockPos);
        final Direction facing = IBlock.getStatePropertySafe(blockState, FACING);
        LiftButtonsBase.LiftButtonDescriptor buttonDescriptor = new LiftButtonsBase.LiftButtonDescriptor(false, false);

        final StoredMatrixTransformations storedMatrixTransformations = new StoredMatrixTransformations(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
        StoredMatrixTransformations storedMatrixTransformations1 = storedMatrixTransformations.copy();
        storedMatrixTransformations1.add(graphicsHolder -> {
            graphicsHolder.rotateYDegrees(-facing.asRotation());
            graphicsHolder.translate(-0.5, 0, 0.060 - SMALL_OFFSET);
        });

        final LinearLayout parentLayout = new LinearLayout(true);
        parentLayout.setBasicsAttributes(world, blockEntity.getPos2());
        parentLayout.setStoredMatrixTransformations(storedMatrixTransformations1);
        parentLayout.setParentDimensions((float) 16 / 16, (float) 11 / 16);
        parentLayout.setPosition((float) 0, (float) 0);
        parentLayout.setWidth(LayoutSize.MATCH_PARENT);//宽度为match_parent，即占满父容器，最底层父容器大小已通过setParentDimensions设置
        parentLayout.setHeight(LayoutSize.MATCH_PARENT);//高度为match_parent，即占满父容器，最底层父容器大小已通过setParentDimensions设置

        if (screenId.equals("test_lift_destination_dispatch_terminal_key_mapping_home")){
             final LinearLayout group1 = new LinearLayout(false);
        group1.setBasicsAttributes(world, blockEntity.getPos2());
        group1.setWidth(LayoutSize.MATCH_PARENT);
        group1.setHeight(LayoutSize.WRAP_CONTENT);
        group1.setMargin(0, 4F/16, 0, 0);

        final LinearLayout group2 = new LinearLayout(false);
        group2.setBasicsAttributes(world, blockEntity.getPos2());
        group2.setWidth(LayoutSize.MATCH_PARENT);
        group2.setHeight(LayoutSize.WRAP_CONTENT);
        group2.setMargin(0, 1F/16, 0, 0);

        final LinearLayout group3 = new LinearLayout(false);
        group3.setBasicsAttributes(world, blockEntity.getPos2());
        group3.setWidth(LayoutSize.MATCH_PARENT);
        group3.setHeight(LayoutSize.WRAP_CONTENT);
        group3.setMargin(0, 1F/16, 0, 0);


        final ButtonView number1 = new ButtonView();
        number1.setId("number1");
        number1.setBasicsAttributes(world, blockEntity.getPos2());
        number1.setWidth(1F / 16);
        number1.setHeight(1F / 16);
        number1.setMargin(1F/16, 0, 0, 0);
        number1.setLight(light);
        number1.setDefaultColor(0xFFFFFFFF);
        number1.setHoverColor(HOVER_COLOR);
        number1.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number1.png"));

        final ButtonView number2 = new ButtonView();
        number2.setId("number2");
        number2.setBasicsAttributes(world, blockEntity.getPos2());
        number2.setWidth(1F / 16);
        number2.setHeight(1F / 16);
        number2.setMargin(1F/16, 0, 0, 0);
        number2.setLight(light);
        number2.setDefaultColor(0xFFFFFFFF);
        number2.setHoverColor(HOVER_COLOR);
        number2.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number2.png"));

        final ButtonView number3 = new ButtonView();
        number3.setId("number3");
        number3.setBasicsAttributes(world, blockEntity.getPos2());
        number3.setWidth(1F / 16);
        number3.setHeight(1F / 16);
        number3.setMargin(1F/16, 0, 0, 0);
        number3.setLight(light);
        number3.setDefaultColor(0xFFFFFFFF);
        number3.setHoverColor(HOVER_COLOR);
        number3.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number3.png"));

        final ButtonView number4 = new ButtonView();
        number4.setId("number4");
        number4.setBasicsAttributes(world, blockEntity.getPos2());
        number4.setWidth(1F / 16);
        number4.setHeight(1F / 16);
        number4.setMargin(1F/16, 0, 0, 0);
        number4.setLight(light);
        number4.setDefaultColor(0xFFFFFFFF);
        number4.setHoverColor(HOVER_COLOR);
        number4.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number4.png"));

        final ButtonView number5 = new ButtonView();
        number5.setId("number5");
        number5.setBasicsAttributes(world, blockEntity.getPos2());
        number5.setWidth(1F / 16);
        number5.setHeight(1F / 16);
        number5.setMargin(1F/16, 0, 0, 0);
        number5.setLight(light);
        number5.setDefaultColor(0xFFFFFFFF);
        number5.setHoverColor(HOVER_COLOR);
        number5.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number5.png"));

        final ButtonView number6 = new ButtonView();
        number6.setId("number6");
        number6.setBasicsAttributes(world, blockEntity.getPos2());
        number6.setWidth(1F / 16);
        number6.setHeight(1F / 16);
        number6.setMargin(1F/16, 0, 0, 0);
        number6.setLight(light);
        number6.setDefaultColor(0xFFFFFFFF);
        number6.setHoverColor(HOVER_COLOR);
        number6.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number6.png"));

        final ButtonView number7 = new ButtonView();
        number7.setId("number7");
        number7.setBasicsAttributes(world, blockEntity.getPos2());
        number7.setWidth(1F / 16);
        number7.setHeight(1F / 16);
        number7.setMargin(1F/16, 0, 0, 0);
        number7.setLight(light);
        number7.setDefaultColor(0xFFFFFFFF);
        number7.setHoverColor(HOVER_COLOR);
        number7.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number7.png"));

        final ButtonView number8 = new ButtonView();
        number8.setId("number8");
        number8.setBasicsAttributes(world, blockEntity.getPos2());
        number8.setWidth(1F / 16);
        number8.setHeight(1F / 16);
        number8.setMargin(1F/16, 0, 0, 0);
        number8.setLight(light);
        number8.setDefaultColor(0xFFFFFFFF);
        number8.setHoverColor(HOVER_COLOR);
        number8.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number8.png"));

        final ButtonView number9 = new ButtonView();
        number9.setId("number9");
        number9.setBasicsAttributes(world, blockEntity.getPos2());
        number9.setWidth(1F / 16);
        number9.setHeight(1F / 16);
        number9.setMargin(1F/16, 0, 0, 0);
        number9.setLight(light);
        number9.setDefaultColor(0xFFFFFFFF);
        number9.setHoverColor(HOVER_COLOR);
        number9.setTexture(new Identifier(top.xfunny.Init.MOD_ID, "textures/block/number9.png"));

        group1.addChild(number1);
        group1.addChild(number2);
        group1.addChild(number3);

        group2.addChild(number4);
        group2.addChild(number5);
        group2.addChild(number6);

        group3.addChild(number7);
        group3.addChild(number8);
        group3.addChild(number9);

        parentLayout.addChild(group1);
        parentLayout.addChild(group2);
        parentLayout.addChild(group3);
        }


        parentLayout.render();

    }
}
