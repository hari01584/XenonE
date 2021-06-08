package cf.androefi.xenone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SimpleInstrumentedTest {

  public SimpleInstrumentedTest(){
    super();
  }

  @Test
  public void fileUpload() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    //assertEquals("cf.androefi.xenone", appContext.getPackageName());
  }
}
