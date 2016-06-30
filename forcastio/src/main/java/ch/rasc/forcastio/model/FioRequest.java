package ch.rasc.forcastio.model;

import java.util.Set;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(depluralize = true)
public interface FioRequest {

	String latitude();

	String longitude();

	/**
	 * When present on a forecast request, return hourly data for the next seven days,
	 * rather than the next two. (This option is ignored on time machine requests).
	 */
	@Nullable
	Boolean extendHourly();

	/**
	 * Return summary properties in the desired language
	 */
	@Nullable
	FioLanguage language();

	/**
	 * Return the API response in units other than the default Imperial units
	 */
	@Nullable
	FioUnit unit();

	/**
	 * Exclude some number of data blocks from the API response. This is useful for
	 * reducing latency and saving cache space.
	 * <p>
	 * By default (when this method and {@link #includeBlocks()} is never called) all
	 * blocks are included.
	 */
	Set<FioBlock> excludeBlocks();

	/**
	 * Include some number of data blocks in the API response. Every block that is not
	 * specifed is automatically excluded.
	 * <p>
	 * By default (when this method and {@link #excludeBlocks()} is never called) all
	 * blocks are included.
	 */
	Set<FioBlock> includeBlocks();

}
