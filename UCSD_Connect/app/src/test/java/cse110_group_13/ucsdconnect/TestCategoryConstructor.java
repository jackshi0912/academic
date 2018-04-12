package cse110_group_13.ucsdconnect;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Christopher on 11/30/2016.
 */

public class TestCategoryConstructor {

    @Test
    public void testCategoryConstructor() throws Exception {

        Category category = new Category(null, null);
        assertEquals(category.name, null);
        assertEquals(category.description, null);

        category = new Category("","");
        assertEquals(category.name, "");
        assertEquals(category.description, "");

        category = new Category("Has Name",null);
        assertEquals(category.name, "Has Name");
        assertEquals(category.description, null);

        category = new Category(null,"Has Description");
        assertEquals(category.name, null);
        assertEquals(category.description, "Has Description");

        category = new Category("Has Name","Has Description");
        assertEquals(category.name, "Has Name");
        assertEquals(category.description, "Has Description");
    }
}
