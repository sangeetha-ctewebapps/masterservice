package com.lic.epgs.rhssomodel;

public class CompositeRoleResponse {
	private String id;
	private String name;
	private boolean composite;
	private boolean clientRole;
	private String containerId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isComposite() {
		return composite;
	}

	public void setComposite(boolean composite) {
		this.composite = composite;
	}

	public boolean isClientRole() {
		return clientRole;
	}

	public void setClientRole(boolean clientRole) {
		this.clientRole = clientRole;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	@Override
	public String toString() {
		return "CompositeRoleResponse [id=" + id + ", name=" + name + ", composite=" + composite + ", clientRole="
				+ clientRole + ", containerId=" + containerId + "]";
	}

}
