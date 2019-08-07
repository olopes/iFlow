package pt.iflow.blocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.iflow.api.blocks.Attribute;
import pt.iflow.api.blocks.Block;
import pt.iflow.api.blocks.Port;

public class BlockBifurcacaoTest extends AbstractBlockTest {
    
    private Block block;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        block = new BlockBifurcacao(MockClassLibrary.GENERAL_INT_RETURN,
                MockClassLibrary.GENERAL_INT_RETURN,
                MockClassLibrary.GENERAL_INT_RETURN,
                MockClassLibrary.JUNIT_FILENAME);
        
        hasInteraction = false;
        bProcInDBRequired = true;
        setFakePorts();
        setFakeAttributes();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        block = null;
    }
    
    @Test
    public void testProperties() {
        assertTrue(block.isEvent() == this.isEvent);
        assertTrue(block.hasEvent() == this.hasEvent);
        assertTrue(block.hasInteraction() == this.hasInteraction);
        assertTrue(block.isCodeGenerator() == this.isCodeGenerator);
        assertTrue(block.isJSPGenerator() == this.isJSPGenerator);
        assertTrue(block.isOnceExec() == this.isOnceExec);
        assertTrue(block.isProcInDBRequired() == this.bProcInDBRequired);
    }
    
    @Test
    public void testGetOutPorts() {
        Port[] ports = block.getOutPorts(userInfo);
        assertTrue(ports.length == 3);
        assertEquals(MockClassLibrary.JUNIT_PORT_OUT, ports[0]
                .getConnectedPortName());
        assertEquals(MockClassLibrary.JUNIT_PORT_SUBPROC, ports[1]
                .getConnectedPortName());
        assertEquals(MockClassLibrary.JUNIT_PORT_ERROR, ports[2]
                .getConnectedPortName());
    }
    
    @Test
    public void testGetEventPort() {
        assertNull(block.getEventPort());
    }
    
    @Test
    public void testGetInPorts() {
        Port[] ports = block.getInPorts(userInfo);
        assertTrue(ports.length == 1);
        assertEquals(MockClassLibrary.JUNIT_PORT_IN, ports[0]
                .getConnectedPortName());
    }
    
    @Test
    public void testBefore() {
        assertEquals("", block.before(userInfo, procData));
    }
    
    @Test
    public void testCanProceed() {
        assertTrue(block.canProceed(userInfo, procData));
    }
    
    @Test
    public void testAfter() {
        assertEquals(MockClassLibrary.JUNIT_PORT_SUBPROC, block.after(userInfo,
                procData).getConnectedPortName());
    }
    
    @Test
    public void testGetDescription() {
        assertEquals("Bifurcação de Processo.", block.getDescription(userInfo,
                procData));
    }
    
    @Test
    public void testGetResult() {
        assertEquals("Bifurcação de Processo Concluída", block.getResult(
                userInfo, procData));
    }
    
    @Test
    public void testGetUrl() {
        assertEquals("", block.getUrl(userInfo, procData));
    }
    
    private void setFakePorts() {
        Port port = new Port();
        port.setConnectedBlockId(MockClassLibrary.GENERAL_INT_RETURN);
        port.setName(MockClassLibrary.JUNIT_PORT);
        port.setConnectedPortName(MockClassLibrary.JUNIT_PORT_ERROR);
        ((BlockBifurcacao) block).portError = port;
        port = new Port();
        port.setConnectedBlockId(MockClassLibrary.GENERAL_INT_RETURN);
        port.setName(MockClassLibrary.JUNIT_PORT);
        port.setConnectedPortName(MockClassLibrary.JUNIT_PORT_EVENT);
        ((BlockBifurcacao) block).portEvent = port;
        port = new Port();
        port.setConnectedBlockId(MockClassLibrary.GENERAL_INT_RETURN);
        port.setName(MockClassLibrary.JUNIT_PORT);
        port.setConnectedPortName(MockClassLibrary.JUNIT_PORT_IN);
        ((BlockBifurcacao) block).portIn = port;
        port = new Port();
        port.setConnectedBlockId(MockClassLibrary.GENERAL_INT_RETURN);
        port.setName(MockClassLibrary.JUNIT_PORT);
        port.setConnectedPortName(MockClassLibrary.JUNIT_PORT_OUT);
        ((BlockBifurcacao) block).portOut = port;
        port = new Port();
        port.setConnectedBlockId(MockClassLibrary.GENERAL_INT_RETURN);
        port.setName(MockClassLibrary.JUNIT_PORT);
        port.setConnectedPortName(MockClassLibrary.JUNIT_PORT_SUBPROC);
        ((BlockBifurcacao) block).portSubProc = port;
    }
    
    private void setFakeAttributes() {
        block.addAttribute(new Attribute(MockClassLibrary._sPROPS_PREFIX
                + MockClassLibrary._sWSDL, MockClassLibrary.JUNIT_WSDL));
        block.addAttribute(new Attribute(MockClassLibrary._sPROPS_PREFIX
                + MockClassLibrary._sSERVICE, MockClassLibrary.JUNIT_SERVICE));
        block.addAttribute(new Attribute(MockClassLibrary._sPROPS_PREFIX
                + MockClassLibrary._sPORT, MockClassLibrary.JUNIT_PORT));
        block.addAttribute(new Attribute(MockClassLibrary._sPROPS_PREFIX
                + MockClassLibrary._sOPERATION,
                MockClassLibrary.JUNIT_OPERATION));
        block.addAttribute(new Attribute(MockClassLibrary._sPROPS_PREFIX
                + MockClassLibrary._sTIMEOUT, ""
                + MockClassLibrary.GENERAL_INT_RETURN));
        block.addAttribute(new Attribute(MockClassLibrary._sPROPS_PREFIX
                + MockClassLibrary._sRETRIES, ""
                + MockClassLibrary.GENERAL_INT_RETURN));
        block.refreshCache(userInfo);
    }
}
