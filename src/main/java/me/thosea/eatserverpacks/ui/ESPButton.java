package me.thosea.eatserverpacks.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;

public final class ESPButton extends ButtonWidget {
	private final Runnable onLeftClick;
	private final Runnable onRightClick;

	public ESPButton(
			Text message,
			Text tooltip,
			int x, int y,
			Runnable onLeftClick,
			Runnable onRightClick) {
		super(
				x, y, 150, 20,
				message, button -> {},
				ButtonWidget.DEFAULT_NARRATION_SUPPLIER
		);

		this.onLeftClick = onLeftClick;
		this.onRightClick = onRightClick;
		this.setTooltip(Tooltip.of(tooltip));
	}

    private boolean clicked(double mouseX, double mouseY) {
        return this.active && this.visible && mouseX >= (double)this.getX() && mouseY >= (double)this.getY() && mouseX < (double)(this.getX() + this.getWidth()) && mouseY < (double)(this.getY() + this.getHeight());
    }

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(!active || !visible) return false;
		if(!this.clicked(mouseX, mouseY)) return false;

		SoundManager manager = MinecraftClient.getInstance().getSoundManager();

		if(button == 0) {
			if(onLeftClick != null) {
				onLeftClick.run();
				playDownSound(manager);
				return true;
			}
		} else if(button == 1) {
			if(onRightClick != null) {
				onRightClick.run();
				playDownSound(manager);
				return true;
			}
		}

		return false;
	}
}