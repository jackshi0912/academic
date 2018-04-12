package cse110_group_13.ucsdconnect;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Christopher on 11/30/2016.
 */

public class TestCategoryEquals {

    @Test
    public void testCategoryConstructor() throws Exception {

        Category category = new Category(null, null);
        Category category1 = new Category(null, null);
        assertEquals(category.equals(category1), true);

        category = new Category("", "");
        assertEquals(category.equals(category1), false);

        category = new Category("Has Name", null);
        assertEquals(category.equals(category1), false);

        category = new Category(null, "Has Description");
        assertEquals(category.equals(category1), true);

        category = new Category("Has Name", "Has Description");
        assertEquals(category.equals(category1), false);


        // test with just description
        category1 = new Category(null, "Has Description");

        category = new Category(null, null);
        assertEquals(category.equals(category1), true);

        category = new Category("", "");
        assertEquals(category.equals(category1), false);

        category = new Category("Has Name", null);
        assertEquals(category.equals(category1), false);

        category = new Category(null, "Has Description");
        assertEquals(category.equals(category1), true);

        category = new Category("Has Name", "Has Description");
        assertEquals(category.equals(category1), false);

        // test with just name
        category1 = new Category("Has Name", null);

        category = new Category(null, null);
        assertEquals(category.equals(category1), false);

        category = new Category("", "");
        assertEquals(category.equals(category1), false);

        category = new Category("Has Name", null);
        assertEquals(category.equals(category1), true);

        category = new Category(null, "Has Description");
        assertEquals(category.equals(category1), false);

        category = new Category("Has Name", "Has Description");
        assertEquals(category.equals(category1), true);

        // test both
        category1 = new Category("Has Name", "Has Description");

        category = new Category(null, null);
        assertEquals(category.equals(category1), false);

        category = new Category("", "");
        assertEquals(category.equals(category1), false);

        category = new Category("Has Name", null);
        assertEquals(category.equals(category1), true);

        category = new Category(null, "Has Description");
        assertEquals(category.equals(category1), false);

        category = new Category("Has Name", "Has Description");
        assertEquals(category.equals(category1), true);
    }
}
