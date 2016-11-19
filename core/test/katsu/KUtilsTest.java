package katsu;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by shaun on 12/11/2016.
 */
public class KUtilsTest {

    @Test
    public void wrap_string_that_doesnt_need_wrapping() throws Exception {
        KUtils utils = new KUtils();
        String result = utils.wrap("foo", 80);
        assertEquals("foo", result);
    }

    @Test
    public void word_same_length_as_limit() throws Exception {
        KUtils utils = new KUtils();
        String result = utils.wrap("spong ebob", 5);
        assertEquals("spong\nebob", result);
    }

    @Test
    public void word_less_than_limit() throws Exception {
        KUtils utils = new KUtils();
        String result = utils.wrap("abc def ghi", 4);
        assertEquals("abc\ndef\nghi", result);
    }

    @Test
    public void hyphenated() throws Exception {
        KUtils utils = new KUtils();
        String result = utils.wrap("abc-def ghi", 4);
        assertEquals("abc\n-def\nghi", result);
    }

}