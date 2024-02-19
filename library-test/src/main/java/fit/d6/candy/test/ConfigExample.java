package fit.d6.candy.test;

import fit.d6.candy.api.configuration.annotation.Category;
import fit.d6.candy.api.configuration.annotation.Comment;
import fit.d6.candy.api.configuration.annotation.Configuration;
import fit.d6.candy.api.configuration.annotation.Node;

import java.util.List;

@Configuration
public class ConfigExample {

    @Node("name")
    @Comment("The name of the target")
    public String name;

    @Node("age")
    @Comment("The age of the target")
    public int age;

    @Node("lines")
    @Comment("just lines")
    public List<String> values = List.of("line1", "line2");

    @Node("test")
    @Category("category")
    @Comment("Category test")
    public String test = "test category";

}
