package cc.polyfrost.oneconfig.gui.elements.config;

import cc.polyfrost.oneconfig.config.annotations.FileOption;
import cc.polyfrost.oneconfig.config.elements.BasicOption;
import cc.polyfrost.oneconfig.gui.elements.text.TextInputField;
import cc.polyfrost.oneconfig.internal.assets.Colors;
import cc.polyfrost.oneconfig.internal.assets.SVGs;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.RenderManager;
import cc.polyfrost.oneconfig.renderer.font.Fonts;
import cc.polyfrost.oneconfig.utils.InputUtils;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Objects;

//todo:
// - fix positioning of icon
// - fix file resetting sometimes for some reason

public class ConfigFile extends BasicOption {
    private final TextInputField textField;

    private final FileType fileType;

    private final boolean folder;
    private final FileOption.EnforceType enforceType;

    public ConfigFile(Field field, Object parent, String name, String category, String subcategory, int size, boolean folder, FileOption.EnforceType enforceType) {
        super(field, parent, name, category, subcategory, size);
        this.folder = folder;
        fileType = field.getType() == File.class ? FileType.FILE : FileType.PATH;
        this.enforceType = enforceType;
        final File file = getAsFile();
        this.textField = new TextInputField(size == 1 ? 256 : 640, 32, file != null ? file.getAbsolutePath() : "", true, false);
    }

    public static ConfigFile create(Field field, Object parent) {
        FileOption file = field.getAnnotation(FileOption.class);
        return new ConfigFile(field, parent, file.name(), file.category(), file.subcategory(), file.size(), file.folder(), file.enforce());
    }

    @Override
    public void draw(long vg, int x, int y) {
        if (!isEnabled()) RenderManager.setAlpha(vg, 0.5f);
        textField.disable(!isEnabled());
        RenderManager.drawText(vg, name, x, y + 16, Colors.WHITE_90, 14, Fonts.MEDIUM);

        final File file = getAsFile();
        if (file != null) {
            textField.setInput(file.getAbsolutePath());
        }

        if (textField.getLines() > 2) textField.setHeight(64 + 24 * (textField.getLines() - 2));
        else textField.setHeight(64);

        textField.draw(vg, x + (size == 1 ? 224 : 352), y);

        boolean hovered = InputUtils.isAreaHovered(x + 967, y + 7, 18, 18) && isEnabled();
        boolean red = false;
        if (file != null) {
            switch (enforceType) {
                case FOLDER_TYPE:
                    red = folder ? file.isFile() : file.isDirectory();
                    break;
                case EXISTS_TYPE:
                    red = !file.exists();
                    break;
                case ALL:
                    red = !file.exists() || (folder ? file.isFile() : file.isDirectory());
                    break;
            }
        }
        int color = hovered ? (red ? Colors.ERROR_800 : Colors.WHITE) : (red ? Colors.ERROR_800_80 : Colors.WHITE_80);
        if (hovered && InputUtils.isClicked()) {
            setFile(folder ? TinyFileDialogs.tinyfd_selectFolderDialog("Select folder", Objects.requireNonNull(getAsFile()).getAbsolutePath()) : TinyFileDialogs.tinyfd_openFileDialog("Select file", getAsFile().getAbsolutePath(), null, null, false));
        }
        if (hovered && Platform.getMousePlatform().isButtonDown(0)) RenderManager.setAlpha(vg, 0.5f);
        RenderManager.drawSvg(vg, SVGs.MAGNIFYING_GLASS_BOLD, x + 967, y + 7, 18, 18, color);

        RenderManager.setAlpha(vg, 1f);
    }

    @Override
    public void keyTyped(char key, int keyCode) {
        if (!isEnabled()) return;
        textField.keyTyped(key, keyCode);
        setFile(textField.getInput());
    }

    private void setFile(String s) {
        try {
            File file = new File(s);
            set(fileType == FileType.FILE ? file : file.toPath());
        } catch (IllegalAccessException ignored) {
        }
    }

    private File getAsFile() {
        try {
            Object object = get();
            if (object == null) return null;
            if (fileType == FileType.FILE) {
                return ((File) object);
            } else {
                return ((Path) object).toFile();
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getHeight() {
        return textField.getHeight();
    }

    private enum FileType {
        FILE, PATH
    }
}
