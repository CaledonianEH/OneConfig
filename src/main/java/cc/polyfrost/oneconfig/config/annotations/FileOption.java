package cc.polyfrost.oneconfig.config.annotations;

import cc.polyfrost.oneconfig.config.data.OptionType;
import cc.polyfrost.oneconfig.internal.config.annotations.Option;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Option(type = OptionType.FILE)
public @interface FileOption {
    String name();

    /**
     * Whether the file is a directory or a file.
     */
    boolean folder();

    /**
     * Whether OneConfig should enforce whether the file is a directory or a file, whether it exists or not, or all of the above.
     */
    EnforceType enforce() default EnforceType.EXISTS_TYPE;

    int size() default 1;

    String category() default "General";

    String subcategory() default "";

    enum EnforceType {
        NONE,
        FOLDER_TYPE,
        EXISTS_TYPE,
        ALL
    }
}
