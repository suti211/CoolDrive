package model;

public class Status {
	String operation;
	boolean success;

	public Status(String op, boolean suc) {
		this.operation = op;
		this.success = suc;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
