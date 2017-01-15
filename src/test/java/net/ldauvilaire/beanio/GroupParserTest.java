package net.ldauvilaire.beanio;

import java.io.*;
import java.util.List;

import org.beanio.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.ldauvilaire.beanio.bean.ControlHeader;
import net.ldauvilaire.beanio.bean.ControlTrailer;
import net.ldauvilaire.beanio.bean.FunctionGroup;
import net.ldauvilaire.beanio.bean.FunctionGroupHeader;
import net.ldauvilaire.beanio.bean.FunctionGroupTrailer;
import net.ldauvilaire.beanio.bean.Transaction;
import net.ldauvilaire.beanio.bean.TransactionSet;
import net.ldauvilaire.beanio.bean.TransactionSetHeader;
import net.ldauvilaire.beanio.bean.TransactionSetTrailer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupParserTest extends ParserTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroupParserTest.class);

	private StreamFactory factory;

    @Before
    public void setup() throws Exception {
    	LOGGER.info("Loading Stream Definition ...");
        factory = newStreamFactory("/data/groups/group.xml");
    }

    @Test
    public void testCsvSubgroups() {
        test("g-csv", "/data/groups/g-csv_subgroups.txt");
    }

    @Test
    public void testFixedLengthSubgroups() {
        test("g-flr", "/data/groups/g-flr_subgroups.txt");
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

        try {
        	int index = 1;
        	LOGGER.info("Reading Iteration {} ...", index);
        	Object item = in.read();
            while (item != null) {

            	if (item instanceof ControlHeader) {
            		ControlHeader controlHeader = (ControlHeader) item;
                	LOGGER.info("Read Control Header, type [{}].", controlHeader.getType());

            	} else if (item instanceof FunctionGroup) {
            		FunctionGroup functionGroup = (FunctionGroup) item;
                	LOGGER.info("Read FunctionGroup.");

                	FunctionGroupHeader header = functionGroup.getFunctionGroupHeader();
                	if (header == null) {
                    	LOGGER.info("   -> No Function Group Header.");
                	} else {
                        String type = header.getType();
                        String name = header.getName();
                    	LOGGER.info("   -> Function Group Header, type [{}], name [{}].", type, name);
                	}

                	List<TransactionSet> transactionSetList = functionGroup.getTransactionSet();
                	if ((transactionSetList == null) || (transactionSetList.isEmpty())) {
                    	LOGGER.info("   -> No Transaction Set.");
                	} else {
                		int nbTransactionSets = transactionSetList.size();
                    	LOGGER.info("   -> List of {} Transaction Set.", nbTransactionSets);
                    	for (int i=0; i<nbTransactionSets; i++) {
                    		TransactionSet transactionSet = transactionSetList.get(i);
                        	LOGGER.info("   -> Transaction Set {}:", i+1);

                        	TransactionSetHeader transactionSetHeader = transactionSet.getHeader();
                        	if (transactionSetHeader == null) {
                            	LOGGER.info("      -> No Transaction Set Header.");
                        	} else {
                        		String type = transactionSetHeader.getType();
                                String name = transactionSetHeader.getName();
                            	LOGGER.info("      -> Transaction Set Header, type [{}], session [{}].", type, name);
                        	}

                        	List<Transaction> transactions = transactionSet.getTransaction();
                        	if ((transactions == null) || (transactions.isEmpty())) {
                            	LOGGER.info("      -> No Transactions.");
                        	} else {
                        		int nbTransactions = transactions.size();
                            	LOGGER.info("      -> List of {} Transactions.", nbTransactions);
                            	for (int j=0; j<nbTransactions; j++) {
                            		Transaction transaction = transactions.get(j);
                            		String type = transaction.getType();
                            		Long amount = transaction.getAmount();
                                	LOGGER.info("      -> Transaction {}, type [{}], amount [{}].", j+1, type, amount);
                            	}
                        	}

                        	TransactionSetTrailer transactionSetTrailer = transactionSet.getTrailer();
                        	if (transactionSetTrailer == null) {
                            	LOGGER.info("      -> No Transaction Set Trailer.");
                        	} else {
                            	LOGGER.info("      -> Transaction Set Trailer, type [{}].", transactionSetTrailer.getType());
                        	}
                    	}
                	}

                	FunctionGroupTrailer trailer = functionGroup.getFunctionGroupTrailer();
                	if (trailer == null) {
                    	LOGGER.info("   -> No Function Group Trailer.");
                	} else {
                    	LOGGER.info("   -> Function Group Trailer, type [{}].", trailer.getType());
                	}

            	} else if (item instanceof ControlTrailer) {
            		ControlTrailer controlTrailer = (ControlTrailer) item;
                	LOGGER.info("Read Control Trailer, type [{}].", controlTrailer.getType());

            	} else {
                	LOGGER.info("Read Item Class = {}.", item.getClass().getSimpleName());
            	}

            	index++;
            	LOGGER.info("Reading Iteration {} ...", index);
            	item = in.read();
            }
        	LOGGER.info("End at Iteration {}.", index);
        }
        catch (BeanReaderException ex) {
            if (errorLineNumber > 0) {
                // assert the line number from the exception matches expected
                Assert.assertEquals(errorLineNumber, ex.getRecordContext().getLineNumber());
            }
            throw ex;
        }
        finally {
            in.close();
        }
    }
}
