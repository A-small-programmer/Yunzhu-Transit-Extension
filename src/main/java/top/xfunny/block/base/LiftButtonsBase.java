package top.xfunny.block.base;

import org.mtr.core.data.Lift;
import org.mtr.core.data.LiftDirection;
import org.mtr.core.operation.PressLift;
import org.mtr.libraries.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.mtr.mapping.holder.*;
import org.mtr.mapping.mapper.*;
import org.mtr.mod.InitClient;
import org.mtr.mod.block.IBlock;
import org.mtr.mod.client.MinecraftClientData;
import org.mtr.mod.packet.PacketPressLiftButton;
import top.xfunny.Init;
import top.xfunny.LiftFloorRegistry;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class LiftButtonsBase extends BlockExtension implements DirectionHelper, BlockWithEntity {
	public static final BooleanProperty UNLOCKED = BooleanProperty.of("unlocked");
	final boolean[] buttonStates = {false, false};
	public LiftButtonsBase() {
		super(BlockHelper.createBlockSettings(true));
	}
	/**
	 * 检查电梯轨道位置是否存在上下按钮的客户端方法
	 * 本方法主要用于确定在给定的电梯轨道位置是否有上下按钮，以及通知相关的回调函数
	 *
	 * @param trackPosition the position of the lift floor track 电梯轨道的位置
	 * @param buttonStates  an array with at least 2 elements: has down button, has up button 一个至少包含两个元素的数组，表示是否有向下和向上的按钮
	 * @param callback      a callback for the lift and floor index, only run if the lift floor track exists in the lift
	 *                      关于电梯和楼层索引的回调函数，只有当电梯地板轨道存在于电梯中时才运行
	 */
	public static void hasButtonsClient(BlockPos trackPosition, boolean[] buttonStates, LiftButtonsBase.FloorLiftCallback callback) {
		// 获取实例中的所有电梯数据
		MinecraftClientData.getInstance().lifts.forEach(lift -> {
			// 获取电梯轨道位置对应的楼层索引
			final int floorIndex = lift.getFloorIndex(Init.blockPosToPosition(trackPosition));
			// 如果楼层索引大于0，则表示存在向下按钮
			if (floorIndex > 0) {
				buttonStates[0] = true;
			}
			// 如果楼层索引在有效范围内（不是顶层也不是底层），则表示存在向上按钮
			if (floorIndex >= 0 && floorIndex < lift.getFloorCount() - 1) {
				buttonStates[1] = true;
			}
			// 如果楼层索引非负，表示电梯中存在该楼层，执行回调函数
			if (floorIndex >= 0) {
				callback.accept(floorIndex, lift);
			}
		});
	}

	@Nonnull
	@Override
	// 处理按钮的使用交互
	public ActionResult onUse2(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		// 检查玩家是否拿着刷子，并尝试更新按钮状态
		final ActionResult result = IBlock.checkHoldingBrush(world, player, () -> {
			final org.mtr.mapping.holder.BlockEntity entity = world.getBlockEntity(pos);
			Init.LOGGER.info(entity.toString());




			final boolean unlocked = !IBlock.getStatePropertySafe(state, UNLOCKED);
			world.setBlockState(pos, state.with(new Property<>(UNLOCKED.data), unlocked));
			player.sendMessage(Text.of((unlocked ? "已解锁" : "已锁定")), true);
			//player.sendMessage((unlocked ? TranslationProvider.GUI_MTR_LIFT_BUTTONS_UNLOCKED : TranslationProvider.GUI_MTR_LIFT_BUTTONS_LOCKED).getText(), true);
		});

		if (result == ActionResult.SUCCESS) {
			return ActionResult.SUCCESS;
		} else {
			// 如果玩家手持电梯连接器或移除器，允许进行其他操作
			if (player.isHolding(top.xfunny.Items.YTE_LIFT_BUTTONS_LINK_CONNECTOR.get()) || player.isHolding(top.xfunny.Items.YTE_LIFT_BUTTONS_LINK_REMOVER.get()) || player.isHolding(top.xfunny.Items.YTE_GROUP_LIFT_BUTTONS_LINK_CONNECTOR.get()) || player.isHolding(top.xfunny.Items.YTE_GROUP_LIFT_BUTTONS_LINK_REMOVER.get())) {
				Init.LOGGER.info("onUse2");
				return ActionResult.PASS;
			}
			else {
				// 检查按钮是否解锁，并处理客户端按钮按下逻辑
				final boolean unlocked = IBlock.getStatePropertySafe(state, UNLOCKED);
				final double hitY = MathHelper.fractionalPart(hit.getPos().getYMapped());

				if (unlocked && hitY < 0.5) {
					// 客户端按钮按下特殊处理
					if (world.isClient()) {
						final org.mtr.mapping.holder.BlockEntity blockEntity = world.getBlockEntity(pos);
						if (blockEntity != null && blockEntity.data instanceof LiftButtonsBase.BlockEntityBase) {
							// 检查并记录上下按钮状态

							((LiftButtonsBase.BlockEntityBase) blockEntity.data).trackPositions.forEach(trackPosition ->
									LiftButtonsBase.hasButtonsClient(trackPosition, buttonStates, (floor, lift) -> {
									}));

							// 根据按钮状态决定电梯方向
							final LiftDirection liftDirection;
							if (buttonStates[0] && buttonStates[1]) {
								liftDirection = hitY < 0.25 ? LiftDirection.DOWN : LiftDirection.UP;
							} else {
								liftDirection = buttonStates[0] ? LiftDirection.DOWN : LiftDirection.UP;
							}

							// 创建并发送按电梯按钮事件
							final PressLift pressLift = new PressLift();
							((LiftButtonsBase.BlockEntityBase) blockEntity.data).trackPositions.forEach(trackPosition ->
									pressLift.add(Init.blockPosToPosition(trackPosition), liftDirection));
							InitClient.REGISTRY_CLIENT.sendPacketToServer(new PacketPressLiftButton(pressLift));

							return ActionResult.SUCCESS;
						} else {
							return ActionResult.FAIL;
						}
					}

					return ActionResult.SUCCESS;
				} else {
					return ActionResult.FAIL;
				}
			}
		}
	}

	@Override
	public BlockState getPlacementState2(ItemPlacementContext ctx) {
		// 获取玩家面对的方向
		final Direction facing = ctx.getPlayerFacing();
		// 根据默认状态和玩家面对的方向来设置方块状态，并返回
		return getDefaultState2().with(new Property<>(FACING.data), facing.data);
	}

	public static class BlockEntityBase extends BlockEntityExtension implements LiftFloorRegistry {

		// 用于在CompoundTag中标识地板位置数组的键
		private static final String KEY_TRACK_FLOOR_POS = "track_floor_pos";
		// 存储需要追踪的位置的集合
		private final ObjectOpenHashSet<BlockPos> trackPositions = new ObjectOpenHashSet<>();


		public BlockEntityBase(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
			super(type, blockPos, blockState);
		}

		/**
		 * 从CompoundTag中读取数据，用于加载位置信息到trackPositions集合中
		 *
		 * @param compoundTag 包含方块实体数据的CompoundTag
		 */
		@Override
		public void readCompoundTag(CompoundTag compoundTag) {
			// 清空当前位置集合，准备加载新的数据
			trackPositions.clear();

			// 从CompoundTag中读取名为KEY_TRACK_FLOOR_POS的长整型数组
			// 每个长整型代表一个BlockPos位置，将其转换并添加到trackPositions集合中
			for (final long position : compoundTag.getLongArray(KEY_TRACK_FLOOR_POS)) {
				trackPositions.add(BlockPos.fromLong(position));
			}
		}

		/**
		 * 重写writeCompoundTag方法，将当前类中的数据保存到CompoundTag中
		 * 主要用于序列化TrackFloor对象的数据，以便存储或传输
		 *
		 * @param compoundTag 一个CompoundTag对象，用于存储TrackFloor的数据
		 */
		@Override
		public void writeCompoundTag(CompoundTag compoundTag) {
			// 创建一个临时的List，用于存储trackPositions的长整型表示
			final List<Long> trackPositionsList = new ArrayList<>();

			// 遍历trackPositions集合，将每个位置转换为长整型并添加到trackPositionsList中
			// 这里的转换是为了以长整型数组的形式存储这些位置信息
			trackPositions.forEach(position -> trackPositionsList.add(position.asLong()));

			// 将收集到的trackPositions长整型列表以数组的形式存储到compoundTag中
			// 使用的键是KEY_TRACK_FLOOR_POS，值是trackPositionsList数组
			compoundTag.putLongArray(KEY_TRACK_FLOOR_POS, trackPositionsList);
		}

		/**
		 * 注册或取消注册一个楼层位置
		 * 该方法用于控制哪些楼层位置被跟踪以及在更改时标记数据为脏
		 *
		 * @param pos   需要注册或取消注册的楼层位置，使用BlockPos表示
		 * @param isAdd 指示是注册还是取消注册的操作类型；true表示注册，false表示取消注册
		 */
		public void registerFloor(BlockPos pos, boolean isAdd) {
			Init.LOGGER.info("正在操作");
			if (isAdd) {
				// 如果是添加操作，则将位置添加到跟踪列表中
				trackPositions.add(pos);
				Init.LOGGER.info("已添加");
			} else {
				// 如果是非添加操作，则从跟踪列表中移除该位置
				trackPositions.remove(pos);
				Init.LOGGER.info("已移除");
			}
			// 更新数据状态，标记数据为“脏”，表示需要保存或同步
			markDirty2();
		}
		/**
		 * 对每个轨道位置执行给定的操作
		 * <p>
		 * 该方法通过遍历所有轨道位置并对其执行给定的操作来提供一种回调机制，这有助于在轨道位置集合上执行统一的操作，
		 * 而无需手动实现遍历逻辑
		 *
		 * @param consumer 执行的操作，接受 {@link BlockPos} 作为参数每个轨道位置都将传递给这个操作
		 */
		public void forEachTrackPosition(Consumer<BlockPos> consumer) {
			trackPositions.forEach(consumer);
		}
	}

	@FunctionalInterface
	public interface FloorLiftCallback {
		void accept(int floor, Lift lift);
	}








}












