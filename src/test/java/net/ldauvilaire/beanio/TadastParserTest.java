package net.ldauvilaire.beanio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.BeanReaderException;
import org.beanio.StreamFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ldauvilaire.beanio.tadast.FlightGroup;
import net.ldauvilaire.beanio.tadast.FlightLegGroup;
import net.ldauvilaire.beanio.tadast.FlightLegRecord;
import net.ldauvilaire.beanio.tadast.FlightRecord;
import net.ldauvilaire.beanio.tadast.HeaderRecord;
import net.ldauvilaire.beanio.tadast.PartnershipRecord;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TadastParserTest extends ParserTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TadastParserTest.class);
	private static final DateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	private static final DateFormat DTF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private StreamFactory factory;

	@Before
	public void setup() throws IOException {
		LOGGER.info("Loading Stream Definition ...");
		factory = newStreamFactory("/data/tadast/tadast-stream.xml");
	}

	@Test
	public void testParseInitWithXmlDefinition() throws IOException {
		test("tadast", "/data/tadast/tadast-init.txt");
	}

	/**
     * Fully parses the given file.
     * @param name the name of the stream
     * @param filename the name of the file to test
     */
    protected void test(String name, String filename) {
        test(name, filename, -1);
    }


    /**
     * Fully parses the given file.
     * @param streamName the name of the stream
     * @param filename the name of the file to test
     * @param errorLineNumber
     */
    protected void test(String streamName, String filename, int errorLineNumber) {

    	LOGGER.info("Testing Stream {} ...", streamName);
    	BeanReader in = factory.createReader(streamName, new InputStreamReader(
            getClass().getResourceAsStream(filename)));

    	test(in, filename, errorLineNumber);
    }

    protected void test(BeanReader in, String filename, int errorLineNumber) {
        try {
        	int index = 1;
        	LOGGER.info("Reading Iteration {} ...", index);
        	Object item = in.read();
            while (item != null) {

            	if (item instanceof HeaderRecord) {
            		HeaderRecord header = (HeaderRecord) item;
                	LOGGER.info("Read Header, type [{}], date [{}].",
                			header.getType(),
                			DF.format(header.getDate()));

            	} else if (item instanceof FlightGroup) {
            		FlightGroup flightGroup = (FlightGroup) item;
                	LOGGER.info("Read FlightGroup.");

                	FlightRecord flight = flightGroup.getFlight();
                	if (flight == null) {
                    	LOGGER.info("   -> No Flight Record.");
                	} else {
                        String type = flight.getType();
                        String airlineCode = flight.getAirlineCode();
                        String flightNumber = flight.getFlightNumber();
                        Date flightIdentifierDate = flight.getFlightIdentifierDate();
                        String operationalSuffix = flight.getOperationalSuffix();
                    	LOGGER.info("   -> Flight, type [{}], code [{}], number [{}], date [{}], suffix [{}].",
                    			type,
                    			airlineCode,
                    			flightNumber,
                    			DF.format(flightIdentifierDate),
                    			operationalSuffix);
                	}

                	List<FlightLegGroup> flightLegs = flightGroup.getFlightLegs();
                	if ((flightLegs == null) || (flightLegs.isEmpty())) {
                    	LOGGER.info("   -> No Flight Legs.");
                	} else {
                		int nbFlightLegs = flightLegs.size();
                    	LOGGER.info("   -> List of {} Flight Legs.", nbFlightLegs);
                    	for (int i=0; i<nbFlightLegs; i++) {
                        	LOGGER.info("   -> Flight Leg {}:", i+1);
                    		FlightLegGroup flightLegGroup = flightLegs.get(i);
                    		FlightLegRecord flightLeg = flightLegGroup.getFlightLeg();
                        	if (flightLeg == null) {
                            	LOGGER.info("      -> No FlightLeg Record.");
                        	} else {
                                String type = flightLeg.getType();
                        		String departureStation = flightLeg.getDepartureStation();
                        		Date departureDate = flightLeg.getDepartureDate();
                        		String arrivalStation = flightLeg.getArrivalStation();
                        		Date arrivalDate = flightLeg.getArrivalDate();
                            	LOGGER.info("      -> Type [{}], departureStation [{}], departureDate [{}], arrivalStation [{}], arrivalDate [{}].",
                            			type,
                            			departureStation,
                            			DTF.format(departureDate),
                            			arrivalStation,
                            			DTF.format(arrivalDate));

                            	List<PartnershipRecord> partnerships = flightLegGroup.getPartnerships();
                            	if (partnerships == null) {
                                	LOGGER.info("      -> No Partnership Record.");
                            	} else {
                            		int nbPartnerships = partnerships.size();
                                	LOGGER.info("      -> List of {} Partnerships.", nbPartnerships);
                                	for (int j=0; j<nbPartnerships; j++) {
                                    	LOGGER.info("      -> Partnership {}:", j+1);
                                    	PartnershipRecord partnership = partnerships.get(j);
                                    	String code1 = partnership.getCode1();
                                    	Integer value1 = partnership.getValue1();
                                    	String code2 = partnership.getCode2();
                                    	Integer value2 = partnership.getValue2();
                                    	String code3 = partnership.getCode3();
                                    	Integer value3 = partnership.getValue3();
                                    	LOGGER.info("         -> Type [{}], 1=>[{}]:[{}], 2=>[{}]:[{}], 3=>[{}]:[{}].",
                                    			partnership.getType(),
                                    			code1,
                                    			value1,
                                    			code2,
                                    			value2,
                                    			code3,
                                    			value3);
                                	}

                            	}
                        	}
                    	}
                	}

            	} else {
                	LOGGER.info("Read Item Class = {}.", item.getClass().getSimpleName());
            	}

            	index++;
            	LOGGER.info("Reading Iteration {} ...", index);
            	item = in.read();
            }
        	LOGGER.info("End at Iteration {}.", index);

        } catch (BeanReaderException ex) {
            if (errorLineNumber > 0) {
                // assert the line number from the exception matches expected
                Assert.assertEquals(errorLineNumber, ex.getRecordContext().getLineNumber());
            }
            throw ex;
        } finally {
            in.close();
        }
    }
}
