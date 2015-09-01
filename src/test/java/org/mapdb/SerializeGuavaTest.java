package org.mapdb;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/* Tests serialization of Guava libraries, see https://github.com/jankotek/mapdb/issues/472 */
public class SerializeGuavaTest {

    @Test
    public void immitable_map_copyOf(){
        File f = TT.tempDbFile();


        Map m = new HashMap();
        m.put(1,2);
        m.put(2,2);
        m.put(3,4);

        m = ImmutableMap.copyOf(m);

        DB db = DBMaker.fileDB(f).transactionDisable().make();
        long recid = db.getEngine().put(m,db.getDefaultSerializer());
        db.commit();
        db.close();
        db = DBMaker.fileDB(f).transactionDisable().make();
        Map m2 = (Map) db.getEngine().get(recid,db.getDefaultSerializer());
        assertEquals(m,m2);
        db.close();

    }

}
