package dto;

public class Status {

	private Operation operation;
	private boolean success;
	private String message;

	public Status() {

	}

	public Status(Operation op, boolean success, String message) {
		this.operation = op;
		this.success = success;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
