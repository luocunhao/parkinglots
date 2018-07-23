package cn.xlink.parkinglots.client.type;

public enum RoleType {
	Xlink(0), /**
	 *
	 */
	Corp(1), /**
	 *
	 */
	User(2), /**
	 *
	 */
	Device(3), /**
	 *
	 */
	Dealer(4), /**
	 *
	 */
	HeavyBuyer(5), /**
	 *
	 */
	EMPOWER(6);
	private final int role;

	private RoleType(int role) {
		this.role = role;
	}

	public int role() {
		return role;
	}
}
