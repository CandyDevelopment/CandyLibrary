package fit.d6.candy.configuration;

import fit.d6.candy.api.configuration.annotation.Category;
import fit.d6.candy.api.configuration.annotation.Comment;
import fit.d6.candy.api.configuration.annotation.Node;
import fit.d6.candy.exception.ConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

public class YamlProvider {

    public static Object load(Class<?> type, File file) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            Object configuration = constructor.newInstance();

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

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

                field.set(configuration, config.get(path));
            }

            return configuration;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Object configuration, File file) throws IllegalAccessException {
        YamlConfiguration config = new YamlConfiguration();
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
                config.setComments(path, List.of(comment.value()));
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
