package eu.wisebed.api.v3;

import eu.wisebed.api.v3.common.NodeUrn;
import eu.wisebed.api.v3.common.NodeUrnPrefix;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeUrnPrefixTest {

	@Test
	public void testThatWisebedPrefixWorks() throws Exception {
		new NodeUrnPrefix("urn:wisebed:uzl1:");
	}

	@Test
	public void testThatUrnPrefixWithOnlyNamespaceIdentifierWorks() throws Exception {
		new NodeUrnPrefix("urn:wisebed:");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatWisebedPrefixWithoutColonThrowsAnException1() throws Exception {
		new NodeUrnPrefix("urn:wisebed:uzl1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatWisebedPrefixWithoutColonThrowsAnException2() throws Exception {
		new NodeUrnPrefix("urn:wisebed");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnPrefixWithHexSuffixThrowsAnException() throws Exception {
		new NodeUrnPrefix("urn:wisebed:uzl1:0x1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnPrefixWithDecSuffixThrowsAnException() throws Exception {
		new NodeUrnPrefix("urn:wisebed:uzl1:1234");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatNodeUrnPrefixWithoutNamespaceIdentifierThrowsAnException() throws Exception {
		new NodeUrnPrefix("urn:");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThatEmptyStringThrowsAnException() throws Exception {
		new NodeUrnPrefix("");
	}

	@Test(expected = NullPointerException.class)
	public void testThatNullThrowsAnException() throws Exception {
		new NodeUrnPrefix(null);
	}

	@Test
	public void testThatNodeUrnBelongsToMatchingNodeUrnPrefix() throws Exception {
		assertTrue(new NodeUrnPrefix("urn:wisebed:uzl1:").belongsTo(new NodeUrn("urn:wisebed:uzl1:0x0001")));
	}

	@Test
	public void testThatNodeUrnDoesNotBelongToNotMatchingNodeUrnPrefix() throws Exception {
		assertFalse(new NodeUrnPrefix("urn:wisebed:uzl2:").belongsTo(new NodeUrn("urn:wisebed:uzl1:0x0001")));
	}

	@Test
	public void testThatNodeUrnDoesNotBelongToNodeUrnPrefixThatIsNotTheFullPrefix() throws Exception {
		assertFalse(new NodeUrnPrefix("urn:wisebed:").belongsTo(new NodeUrn("urn:wisebed:uzl1:0x0001")));
	}

	@Test(expected = NullPointerException.class)
	public void testThatBelongsToThrowsNPEIfArgumentIsNull() throws Exception {
		new NodeUrnPrefix("urn:wisebed:uzl1:").belongsTo(null);
	}
}