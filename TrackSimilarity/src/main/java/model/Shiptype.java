package model;

public enum Shiptype {

	FISHING(30, 30), TUG(31, 32), DREDGER(33, 33), DIVEVESSEL(34, 34), MILITARYOPS(35, 35), SAILINGVESSEL(36,
			36), PLEASURECRAFT(37, 37), HIGHSPEEDCRAFT(40, 49), PILOTVESSEL(50, 50), SAR(51,
					51), TUG2(52, 52), PORTTENDER(53, 53), ANTIPOLLUTION(54, 54), LAWENFORCE(55, 55), LOCALVESSEL(56,
							57), MEDICALTRANS(58, 58), SPECIAL_CRAFT(59, 59), PASSENGER(60, 69), CARGO(70,
									70), CARGO_HAZARD_A(71, 71), CARGO_HAZARD_B(72, 72), CARGO_HAZARD_C(73,
											73), CARGO_HAZARD_D(74, 74), CARGO2(75, 79), TANKER(80,
													80), TANKER_HAZARD_A(81, 81), TANKER_HAZARD_B(82,
															82), TANKER_HAZARD_C(83, 83), TANKER_HAZARD_D(84,
																	84), TANKER2(85, 89), OTHER(90, 99);

	private final int start;
	private final int end;

	Shiptype(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return this.start;
	}

	public int getEnd() {
		return this.end;
	}

	/**
	 * Checks whether the given value is between start and end of the type.
	 * 
	 * @param value
	 * @return
	 */
	public boolean isInInterval(int value) {

		if (value >= start && value <= end) {
			return true;
		} else {
			return false;
		}
	}

}
