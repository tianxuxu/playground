package ch.rasc.nosql.rethink;

import java.util.List;

public class InsertResponse {

	private Long deleted;
	private Long inserted;
	private Long unchanged;
	private Long replaced;
	private Long errors;
	private Long skipped;

	private List<String> generated_keys;

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getInserted() {
		return inserted;
	}

	public void setInserted(Long inserted) {
		this.inserted = inserted;
	}

	public Long getUnchanged() {
		return unchanged;
	}

	public void setUnchanged(Long unchanged) {
		this.unchanged = unchanged;
	}

	public Long getReplaced() {
		return replaced;
	}

	public void setReplaced(Long replaced) {
		this.replaced = replaced;
	}

	public Long getErrors() {
		return errors;
	}

	public void setErrors(Long errors) {
		this.errors = errors;
	}

	public Long getSkipped() {
		return skipped;
	}

	public void setSkipped(Long skipped) {
		this.skipped = skipped;
	}

	public List<String> getGenerated_keys() {
		return generated_keys;
	}

	public void setGenerated_keys(List<String> generated_keys) {
		this.generated_keys = generated_keys;
	}

	@Override
	public String toString() {
		return "InsertResponse [deleted=" + deleted + ", inserted=" + inserted
				+ ", unchanged=" + unchanged + ", replaced=" + replaced + ", errors="
				+ errors + ", skipped=" + skipped + ", generated_keys=" + generated_keys
				+ "]";
	}

}
