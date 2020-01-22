package bayeos.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import bayeos.frame.FrameParserException;
import bayeos.frame.Parser;
import bayeos.serialframe.ComPortDevice;


public class LoggerDeviceTest {

	public final String comPort = "COM45";

	Logger logger = null;
	ComPortDevice com = null;
	
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();


	@Before
	public void setUp() throws IOException {
		com = new ComPortDevice();
		com.open(comPort);		
		logger = new Logger(com);		
	}

	@After
	public void tearDown() throws IOException {					
		com.close();
	}

	@Test
	public void setName() throws Exception {
		System.out.println("Set name");
		logger.setName("Hallo");
		System.out.println("Get name");
		assertEquals("Hallo", logger.getName());
	}

	
	@Test
	public void setTime() throws Exception {
		System.out.println("Set Time");
		Date a = new Date();
		logger.setTime(a);
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {

		}
		Date b = logger.getTime();
		assertTrue(b.after(a));
	}
	
	@Test
	public void setSamplingInterval() throws Exception {
		System.out.println("Set sampling interval");
		logger.setSamplingInterval(60);
		
		assertEquals(60, logger.getSamplingInterval());				
	}
	
	@Test 
	public void getVersion() throws Exception {
		System.out.println("Get Version");
		String version = logger.getVersion();
		assertNotNull(version);		
	}
		
	
	
	@Test
	public void sendBreak() {
		try {
			logger.breakSocket();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	
	@Test
	public void readAndParseBulk() throws IOException, FrameParserException {
							
			File file = testFolder.newFile("BAYEOS.DB");
			// file.deleteOnExit();			
			System.out.println("Dumping to " + file.getAbsolutePath());
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
			BulkWriter bWriter = new BulkWriter(bout);
			long read = 0;			
			long bytes = logger.startBulkData(LoggerConstants.DM_FULL);
			System.out.println("Reading:" + bytes + " Bytes");
									
			long c = 0;
			while (read < bytes) {
				System.out.println(c++);
				System.out.println( + read + "/" + bytes + " bytes");
				byte[] bulk = logger.readBulk();
				System.out.println("Bulk size:" + bulk.length);
				bWriter.write(bulk);
				bout.flush();
				read = read + bulk.length-5;												
												
			}
			bout.flush();
			bout.close();
			System.out.println("Read:" + read + " Bytes");			
			
			BulkReader reader = new BulkReader(new FileInputStream(file));									
			byte[] data = null;			
			while ((data = reader.readData())!=null){
				Parser.parse(data);
			}																								
	} 
	
	@Test
	public void testLiveMode() {		
			try {
				logger.getVersion();
				logger.startLiveData();	
				long f = 0;
				while(true) {
					byte[] b = logger.readData();
					if (b!= null) {
						if (++f==2) break;	
						try {
							System.out.println("Frame: " + f + " Content:" + Parser.parse(b));
						} catch (FrameParserException e) {
							System.out.println(e.getMessage());
						}																
					} else {					
						Thread.sleep(1000);					
					}					
				}				
				logger.stopLiveData();
				Thread.sleep(100000);
									 				
				if (com.available() > 0) {	
					byte[] frame = logger.readData();					
					fail("Found unexpexcted Frame:" + Parser.parse(frame));							
				}
								
				
			} catch (IOException | InterruptedException | FrameParserException e) {
				fail(e.getMessage());
			}
	}

	
	

}