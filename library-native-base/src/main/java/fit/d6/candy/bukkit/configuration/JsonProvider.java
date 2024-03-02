package fit.d6.candy.bukkit.configuration;

import com.google.gson.*;
import fit.d6.candy.api.configuration.annotation.Category;
import fit.d6.candy.api.configuration.annotation.Node;
import fit.d6.candy.bukkit.exception.ConfigurationException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonProvider {

    private final static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static Object load(Class<?> type, File file) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            Object configuration = constructor.newInstance();

            JsonObject config = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

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

                JsonObject targetObject = config;

                Category category = field.getAnnotation(Category.class);
                if (category != null) {
                    for (String categoryNode : category.value()) {
                        if (!targetObject.has(categoryNode))
                            continue;
                        if (!targetObject.get(categoryNode).isJsonObject())
                            continue;
                        targetObject = targetObject.getAsJsonObject(categoryNode);
                    }
                }

                JsonElement element = targetObject.get(path);
                if (element.isJsonPrimitive()) {
                    JsonPrimitive primitive = element.getAsJsonPrimitive();
                    if (primitive.isString()) {
                        if (field.getType() == Character.class || field.getType() == char.class) {
                            field.set(configuration, primitive.getAsString().charAt(0));
                        } else {
                            field.set(configuration, primitive.getAsString());
                        }
                    } else if (primitive.isNumber()) {
                        if (field.getType() == Integer.class || field.getType() == int.class) {
                            field.set(configuration, primitive.getAsInt());
                        } else if (field.getType() == Double.class || field.getType() == double.class) {
                            field.set(configuration, primitive.getAsDouble());
                        } else if (field.getType() == Short.class || field.getType() == short.class) {
                            field.set(configuration, primitive.getAsShort());
                        } else if (field.getType() == Long.class || field.getType() == long.class) {
                            field.set(configuration, primitive.getAsLong());
                        } else if (field.getType() == Byte.class || field.getType() == byte.class) {
                            field.set(configuration, primitive.getAsByte());
                        } else if (field.getType() == Float.class || field.getType() == float.class) {
                            field.set(configuration, primitive.getAsFloat());
                        } else if (field.getType() == BigInteger.class) {
                            field.set(configuration, primitive.getAsBigInteger());
                        } else if (field.getType() == BigDecimal.class) {
                            field.set(configuration, primitive.getAsBigDecimal());
                        } else if (field.getType() == Number.class) {
                            field.set(configuration, primitive.getAsNumber());
                        }
                    } else if (primitive.isBoolean()) {
                        field.set(configuration, primitive.getAsBoolean());
                    }
                } else if (element.isJsonNull()) {
                    field.set(configuration, null);
                } else if (List.class.isAssignableFrom(field.getType()) && element.isJsonArray()) {
                    field.set(configuration, parseList(element.getAsJsonArray()));
                } else if (JsonElement.class.isAssignableFrom(field.getType())) {
                    field.set(configuration, element);
                }
            }

            return configuration;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException |
                 FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Object configuration, File file) throws IllegalAccessException {
        JsonObject jsonObject = new JsonObject();
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

            JsonObject targetObject = jsonObject;

            Category category = field.getAnnotation(Category.class);
            if (category != null) {
                for (String categoryNode : category.value()) {
                    if (targetObject.has(categoryNode) && !targetObject.get(categoryNode).isJsonObject())
                        throw new ConfigurationException("Configuration path crosses");


                    JsonObject newJo;
                    if (!targetObject.has(categoryNode)) {
                        newJo = new JsonObject();
                        targetObject.add(categoryNode, newJo);
                    } else {
                        newJo = targetObject.getAsJsonObject(categoryNode);
                    }

                    targetObject = newJo;
                }
            }

            Object safedValue = field.get(configuration);

            if (List.class.isAssignableFrom(field.getType())) {
                safedValue = parseArray((List<?>) safedValue);
            }

            if (safedValue == null) {
                targetObject.add(path, JsonNull.INSTANCE);
            } else if (safedValue instanceof String string) {
                targetObject.addProperty(path, string);
            } else if (safedValue instanceof Number number) {
                targetObject.addProperty(path, number);
            } else if (safedValue instanceof Boolean bool) {
                targetObject.addProperty(path, bool);
            } else if (safedValue instanceof Character character) {
                targetObject.addProperty(path, character);
            } else if (safedValue instanceof JsonElement element) {
                targetObject.add(path, element);
            } else {
                throw new ConfigurationException("Illegal object");
            }
        }

        try {
            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(GSON.toJson(jsonObject));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<?> parseList(JsonArray array) {
        List<Object> list = new ArrayList<>();
        for (JsonElement element : array) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isString()) {
                    list.add(primitive.getAsString());
                } else if (primitive.isNumber()) {
                    list.add(primitive.getAsNumber());
                } else if (primitive.isBoolean()) {
                    list.add(primitive.getAsBoolean());
                }
            } else if (element.isJsonNull()) {
                list.add(null);
            } else if (element.isJsonArray()) {
                list.add(parseList(element.getAsJsonArray()));
            } else {
                list.add(element);
            }
        }
        return list;
    }

    private static JsonArray parseArray(List<?> list) {
        JsonArray array = new JsonArray();
        for (Object object : list) {
            if (object == null) {
                array.add(JsonNull.INSTANCE);
            } else if (object instanceof String string) {
                array.add(string);
            } else if (object instanceof Number number) {
                array.add(number);
            } else if (object instanceof Boolean bool) {
                array.add(bool);
            } else if (object instanceof Character character) {
                array.add(character);
            } else if (object instanceof JsonElement element) {
                array.add(element);
            } else if (object instanceof List<?> anotherList) {
                array.add(parseArray(anotherList));
            } else {
                throw new ConfigurationException("Illegal object");
            }
        }
        return array;
    }

}
