package gov.nysenate.openleg.processor.bill;

import gov.nysenate.openleg.BaseTests;
import gov.nysenate.openleg.model.sourcefiles.LegDataFragment;
import org.junit.Test;

/**
 * Created by uros on 2/14/17.
 */
public class XmlBillStatusProcessorTest extends BaseTests {

    @Test
    public void test()  {
        process("billstatfile.xml");
    }

    private LegDataFragment process(String filename)   {

        return null;
    }

}
