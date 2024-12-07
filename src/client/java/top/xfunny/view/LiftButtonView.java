package top.xfunny.view;

import org.mtr.mapping.holder.*;
import org.mtr.mod.Init;
import org.mtr.mod.block.IBlock;
import org.mtr.mod.client.IDrawing;
import org.mtr.mod.render.MainRenderer;
import org.mtr.mod.render.QueuedRenderLayer;
import org.mtr.mod.render.StoredMatrixTransformations;
import top.xfunny.block.TestLiftButtons;
import top.xfunny.block.base.LiftButtonsBase;

import static org.mtr.mapping.mapper.DirectionHelper.FACING;
import static org.mtr.mod.data.IGui.SMALL_OFFSET;

public class LiftButtonView implements RenderView {

    // 按钮状态、颜色、位置等字段
    private boolean[] buttonStates = {false, false};  // 上下按钮状态
    private Identifier upButtonTexture, downButtonTexture;
    private int defaultUpColor, defaultDownColor, hoverUpColor, hoverDownColor, pressedUpColor, pressedDownColor;
    private BlockPos blockPos;
    private Direction facing;
    private BlockState blockState;
    private World world;
    private StoredMatrixTransformations storedMatrixTransformations, storedMatrixTransformations1;
    private LiftButtonsBase.LiftButtonDescriptor buttonDescriptor;
    private int light;
    private float width, height, x, y, yUp, yDown;
    private float marginLeft, marginTop, marginRight, marginBottom, spacing;
    private String id = "button";
    private Gravity gravity;
    private boolean canHover, lookingAtTopHalf, lookingAtBottomHalf;

    @Override
    public String getId() {
        return id;
    }

    // 渲染逻辑
    public void render() {
        this.blockState = world.getBlockState(blockPos);
        this.facing = IBlock.getStatePropertySafe(blockState, FACING);
        this.storedMatrixTransformations1 = storedMatrixTransformations.copy();

        storedMatrixTransformations1.add(graphicsHolder -> {
            graphicsHolder.rotateYDegrees(-facing.asRotation());
            graphicsHolder.translate(0, 0, 0.4375 - SMALL_OFFSET);
        });
        dynamicRender();
    }

    private void dynamicRender() {
        final HitResult hitResult = MinecraftClient.getInstance().getCrosshairTargetMapped();
        positionY(y, spacing);

        // 判断鼠标是否悬停在按钮上
        if (hitResult == null || !IBlock.getStatePropertySafe(blockState, TestLiftButtons.UNLOCKED)) {
            lookingAtTopHalf = lookingAtBottomHalf = false;
        } else {
            final Vector3d hitLocation = hitResult.getPos();
            final double hitY = MathHelper.fractionalPart(hitLocation.getYMapped());
            final boolean inBlock = hitY < 0.5 && Init.newBlockPos(hitLocation.getXMapped(), hitLocation.getYMapped(), hitLocation.getZMapped()).equals(blockPos);
            lookingAtTopHalf = inBlock && (!buttonDescriptor.hasDownButton() || hitY > 0.25);
            lookingAtBottomHalf = inBlock && (!buttonDescriptor.hasUpButton() || hitY < 0.25);
        }

        // 如果有下按钮，进行渲染
        if (buttonDescriptor.hasDownButton()) {
            MainRenderer.scheduleRender(
                downButtonTexture, false, queuedRenderLayerRegulator(lookingAtBottomHalf,buttonStates[0]),
                (graphicsHolder, offset) -> {
                    storedMatrixTransformations1.transform(graphicsHolder, offset);
                    IDrawing.drawTexture(
                        graphicsHolder,
                        x, yDown, width, height,
                        0, 0, 1, 1,
                        facing,
                        buttonStates[0] ? pressedDownColor : (lookingAtBottomHalf ? hoverDownColor : defaultDownColor),
                        light
                    );
                    graphicsHolder.pop();
                }
            );
        }

        // 如果有上按钮，进行渲染
        if (buttonDescriptor.hasUpButton()) {
            MainRenderer.scheduleRender(
                upButtonTexture, false, queuedRenderLayerRegulator(lookingAtTopHalf,buttonStates[1]),
                (graphicsHolder, offset) -> {
                    storedMatrixTransformations1.transform(graphicsHolder, offset);
                    IDrawing.drawTexture(
                        graphicsHolder,
                        x, yUp, width, height,
                        0, 1, 1, 0,
                        facing,
                        buttonStates[1] ? pressedUpColor : (lookingAtTopHalf ? hoverUpColor : defaultUpColor),
                        light
                    );
                    graphicsHolder.pop();
                }
            );
        }
    }

    private QueuedRenderLayer queuedRenderLayerRegulator(Boolean looking,Boolean buttonState) {
        return ((looking || buttonState) & canHover) ? QueuedRenderLayer.LIGHT_TRANSLUCENT : QueuedRenderLayer.EXTERIOR;
    }

    private void positionY(float y, float spacing) {
        yUp = buttonDescriptor.hasDownButton() ? y + height + spacing : y;
        yDown = y;
    }

    // 布局方法
    @Override
    public void setStoredMatrixTransformations(StoredMatrixTransformations storedMatrixTransformations) {
        this.storedMatrixTransformations = storedMatrixTransformations;
    }

    @Override
    public float[] getMargin() {
        return new float[]{marginLeft, marginTop, marginRight, marginBottom};
    }

    @Override
    public Gravity getGravity() {
        return gravity;
    }

    @Override
    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return buttonDescriptor.hasUpButton() && buttonDescriptor.hasDownButton() ? height * 2 + spacing : height;
    }

    @Override
    public void setMargin(float left, float top, float right, float bottom) {
        this.marginLeft = left;
        this.marginTop = top;
        this.marginRight = right;
        this.marginBottom = bottom;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // 不适用的布局方法
    @Override
    public void setParentDimensions(float parentWidth, float parentHeight) {}

    @Override
    public void calculateLayoutWidth() {}

    @Override
    public void calculateLayoutHeight() {}

    @Override
    public float[] calculateChildGravityOffset(float childWidth, float childHeight, float[] childMargin, Gravity childGravity) {
        return new float[0];
    }

    @Override
    public Object getParentType() {
        return null;
    }

    @Override
    public void setParentType(Object thisObject) {}

    // 设置基本属性的方法
    public void setBasicsAttributes(World world, BlockPos blockPos, LiftButtonsBase.LiftButtonDescriptor descriptor) {
        this.world = world;
        this.blockPos = blockPos;
        this.buttonDescriptor = descriptor;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public void setDownButtonLight() {
        buttonStates[0] = true;
    }

    public void setUpButtonLight() {
        buttonStates[1] = true;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultUpColor = defaultColor;
        this.defaultDownColor = defaultColor;
    }

    public void setDefaultColor(int defaultUpColor, int defaultDownColor) {
        this.defaultUpColor = defaultUpColor;
        this.defaultDownColor = defaultDownColor;
    }

    public void setHoverColor(int hoverColor) {
        this.hoverUpColor = hoverColor;
        this.hoverDownColor = hoverColor;
    }

    public void setHoverColor(int hoverUpColor, int hoverDownColor) {
        this.hoverUpColor = hoverUpColor;
        this.hoverDownColor = hoverDownColor;
    }

    public void setPressedColor(int pressedColor) {
        this.pressedUpColor = pressedColor;
        this.pressedDownColor = pressedColor;
    }

    public void setPressedColor(int pressedUpColor, int pressedDownColor) {
        this.pressedUpColor = pressedUpColor;
        this.pressedDownColor = pressedDownColor;
    }

    public void setTexture(Identifier texture) {
        this.upButtonTexture = texture;
        this.downButtonTexture = texture;
    }

    public void setTexture(Identifier upButtonTexture, Identifier downButtonTexture) {
        this.upButtonTexture = upButtonTexture;
        this.downButtonTexture = downButtonTexture;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setHover(Boolean hover){
        this.canHover = hover;
    }
}
