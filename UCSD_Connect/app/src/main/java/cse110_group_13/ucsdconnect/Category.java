package cse110_group_13.ucsdconnect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jack on 11/7/2016.
 *
 * Description: This is a simple class that holds category item data.
 *
 * Public Methods: Category(String name, String description)
 *                 equals(Object other)
 *                 hashcode()
 *
 */

public class Category implements Serializable{
    public final String name;
    public final String description;

    // list of starter categories
    public static final List<Category> categoryList = new ArrayList<Category>(Arrays.asList(
            new Category("Sports", "Playing or watching sports"),
            new Category("Academic", "Study groups, tutor help, etc"),
            new Category("Club", "UCSD's clubs on campus"),
            new Category("Other", "None of the above")
    ));

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Category)) {
            return false;
        }

        Category otherCategory = (Category) other;
        if (name == null && otherCategory.name == null) {
            return true;
        } else if (name == null || otherCategory.name == null) {
            return false;
        }

        return name.equals(otherCategory.name);
    }

    public int hashcode() {
        return name.hashCode();
    }
}
