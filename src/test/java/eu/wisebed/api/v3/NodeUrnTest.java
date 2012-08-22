package eu.wisebed.api.v3;

import eu.wisebed.api.v3.common.NodeUrn;
import eu.wisebed.api.v3.common.NodeUrnPrefix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeUrnTest {

	@Test
	public void testThatNodeUrnWithHexSuffixWorks() throws Exception {
		new NodeUrn("urn:wisebed:uzl1:0x1234");
		new NodeUrn("urn:wisebed:0x1234");
	}

	@Test
	public void testThatNodeUrnWithDecSuffixWorks() throws Exception {
		new NodeUrn("urn:wisebed:uzl1:1234");
		new NodeUrn("urn:wisebed:1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnWithoutNamespaceIdentifierThrowsAnException() throws Exception {
		new NodeUrn("urn:1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnWithoutNamespaceIdentifierThrowsAnException2() throws Exception {
		new NodeUrn("urn:portal");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnWithoutUrnPrefixThrowsAnException() throws Exception {
		new NodeUrn("wisebed:uzl1:0x1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnWithoutMacSuffixThrowsAnException() throws Exception {
		new NodeUrn("urn:wisebed:uzl1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnPrefixShouldThrowAnException() throws Exception {
		new NodeUrn("urn:wisebed:uzl1:");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatEmptyStringThrowsAnException() throws Exception {
		new NodeUrn("");
	}

	@Test(expected = NullPointerException.class)
	public void testThatNullThrowsAnException() throws Exception {
		new NodeUrn(null);
	}

	@Test
	public void testThatNodeUrnBelongsToMatchingNodeUrnPrefix() throws Exception {
		assertTrue(new NodeUrn("urn:wisebed:uzl1:0x0001").belongsTo(new NodeUrnPrefix("urn:wisebed:uzl1:")));
	}

	@Test
	public void testThatNodeUrnDoesNotBelongToNotMatchingNodeUrnPrefix() throws Exception {
		assertFalse(new NodeUrn("urn:wisebed:uzl1:0x0001").belongsTo(new NodeUrnPrefix("urn:wisebed:uzl2:")));
	}

	@Test
	public void testThatNodeUrnDoesNotBelongToNodeUrnPrefixThatIsNotTheFullPrefix() throws Exception {
		assertFalse(new NodeUrn("urn:wisebed:uzl1:0x0001").belongsTo(new NodeUrnPrefix("urn:wisebed:")));
	}

	@Test(expected = NullPointerException.class)
	public void testThatBelongsToThrowsNPEIfArgumentIsNull() throws Exception {
		new NodeUrn("urn:wisebed:uzl1:0x0001").belongsTo(null);
	}

	@Test
	public void testThatSuffixIsReturnedCorrectly() throws Exception {
		assertEquals("0x0001", new NodeUrn("urn:wisebed:uzl1:0x0001").getSuffix());
	}
}