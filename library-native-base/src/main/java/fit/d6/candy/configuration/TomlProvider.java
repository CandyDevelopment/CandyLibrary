package fit.d6.candy.configuration;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.ParsingMode;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import fit.d6.candy.api.configuration.annotation.Category;
import fit.d6.candy.api.configuration.annotation.Comment;
import fit.d6.candy.api.configuration.annotation.Node;
import fit.d6.candy.exception.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

public class TomlProvider {

    public static Object load(Class<?> type, File file) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            Object configuration = constructor.newInstance();

            CommentedConfig config = CommentedConfig.inMemory();

            TomlParser parser = TomlFormat.instance().createParser();
            parser.parse(new FileInputStream(file), config, ParsingMode.REPLACE, StandardCharsets.UTF_8);

            for (Field field : type.getDeclaredFields()) {
                if (!Modifier.isPublic(field.getModifiers()))
                    continue;
                if (Modifier.isFinal(field.getModifiers()))
                    continue;
                if (Modifier.isStatic(field.getModifiers()))
                    continue;

                Node node = field.getAnnotation(Node.class);
                if (node == null)
                    continue;

                String path = node.value();

                Category category = field.getAnnotation(Category.class);
                if (category != null) {
                    StringBuilder builder = new StringBuilder();
                    for (String categoryNode : category.value()) {
                        if (categoryNode.contains("."))
                            throw new ConfigurationException("Illegal character \".\" appears");
                        builder.append(categoryNode).append(".");
                    }
                    path = builder + path;
                }

                if (config.contains(path))
                    continue;

                field.set(configuration, config.get(path));
            }

            return configuration;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException |
                 FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Object configuration, File file) throws IllegalAccessException {
        CommentedConfig config = CommentedConfig.inMemory();

        for (Field field : configuration.getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(field.getModifiers()))
                continue;
            if (Modifier.isFinal(field.getModifiers()))
                continue;
            if (Modifier.isStatic(field.getModifiers()))
                continue;

            Node node = field.getAnnotation(Node.class);
            if (node == null)
                continue;

            String path = node.value();

            Category category = field.getAnnotation(Category.class);
            if (category != null) {
                StringBuilder builder = new StringBuilder();
                for (String categoryNode : category.value()) {
                    if (categoryNode.contains("."))
                        throw new ConfigurationException("Illegal character \".\" appears");
                    builder.append(categoryNode).append(".");
                }
                path = builder + path;
            }

            config.set(path, field.get(configuration));

            Comment comment = field.getAnnotation(Comment.class);
            if (comment != null) {
                config.setComment(path, String.join(" ", comment.value()));
            }
        }

        TomlWriter writer = TomlFormat.instance().createWriter();
        writer.write(config, file, WritingMode.REPLACE, StandardCharsets.UTF_8);
    }

}
