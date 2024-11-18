package unitTests;

import issPageClasses.AppointmentCalendarPage;
import issPageClasses.ChangeLocationModalPage;
import issPageClasses.HeaderModalPage;
import issPageClasses.HomePage;
import issPageClasses.PatientEligibilityModalPage;
import issPageClasses.PatientHeaderModalPage;
import issPageClasses.PatientPage;
import issPageClasses.PatientRecordFlagsModalPage;
import issPageClasses.PreviewPatientLetterModalPage;
import issPageClasses.Resources;
import issPageClasses.SensitiveRecordModalPage;
import issPageClasses.SignOnPage;
import platformIndependentCore.scripts.Arguments;
import platformIndependentCore.scripts.TestScript;
import platformIndependentCore.utilities.ConfigProperties;

/**
 * <b>Name :</b> ClinicSearchAndSelectStartTimeIncementSlotCounts.java
 * <p>
 * <b>Generated :</b> Sep 12, 2024
 * <p>
 * <b>Description :</b>
 * <p>
 *
 * @since Sep 12, 2024
 * @author OITSDCLEWIST
 */
public class ClinicSearchAndSelectStartTimeIncementSlotCounts extends TestScript {

	/** URL for SSOI login for the active test environment */
	protected static final String BROWSER = ConfigProperties.getValue("BROWSER");

	/**
	 * Main method
	 *
	 * @param args Args
	 */
	public static void main(String[] args) {
		runScript(args);
	}

	@Override
	public void testScript(Arguments args) {
		// TODO ENTER SCRIPT CODE HERE

		// page classes used

		HomePage hp = new HomePage();
		ChangeLocationModalPage clmp = new ChangeLocationModalPage();
		PatientHeaderModalPage phmp = new PatientHeaderModalPage();
		SensitiveRecordModalPage srmp = new SensitiveRecordModalPage();
		PatientRecordFlagsModalPage prfmp = new PatientRecordFlagsModalPage();
		PatientEligibilityModalPage pemp = new PatientEligibilityModalPage();
		PatientPage pp = new PatientPage();
		HeaderModalPage hmp = new HeaderModalPage();
		Resources r = new Resources();

		SignOnPage sp = new SignOnPage();
		AppointmentCalendarPage acp = new AppointmentCalendarPage();

		PreviewPatientLetterModalPage pplmp = new PreviewPatientLetterModalPage();

		hp.loadPage();

		sp.clickSignInWithWindowsAuthentication();
		sleep(10);

		hmp.clickBtnFacilityLocation();
		sleep(3);

		// Select desire facility
		clmp.selectFacilityLocationByName("958: 958 Cheyenne VA Medical Center", "958: 958 Cheyenne VA Medical Center");
		sleep(3);

		// enter search string to cause name page to appear
		phmp.setSearchPatientInput("TEST,AKASH");
		sleep(5);

		phmp.selectPatientByName("TEST,AKASH");
		sleep(2);

		// Click on Patient Eligibility Acknowledge Button
		pemp.clickBtnPatientEligibilityAcknowledge();
		sleep(2);
		hmp.clickBtnHome();
		sleep(3);
		// Verify and Click the Clinic Radio Button
		hp.clickTabClinic();

		hp.setSearchClinicInput("CHY CARDIOLOGY");
		sleep(5);

		// select the clinic by name
		hp.selectClinicByName("CHY CARDIOLOGY");
		sleep(8);
		// Fetch this data from the Patient.csv
		String tsDateBegin = "9/20/2024";
		String tsStartTimeBegin = "8:00 AM";

		if (tsDateBegin.equalsIgnoreCase("today")) {
			tsDateBegin = r.readCurrentDate("M/d/yyyy");
			System.out.println(tsDateBegin);
		}

		r.scrollIntoView(acp.selectTimeSlotBySeparateDateTime(tsDateBegin, tsStartTimeBegin));

		r.clickBtnLeftMouseAfterMove(acp.selectScheduleDateTimeSlotCount(tsDateBegin, tsStartTimeBegin));
		sleep(5);

		acp.getTimeSlotViewerAppointment(tsDateBegin);
		sleep(5);
		vpEquals("Read label Slot Count", "1:", acp.readLblSlotCount());
		sleep(3);
		vpEquals("Read label Patient Name", "SEAN,ACTIVE DUTY EVELYN", acp.readLblTimeSlotNameCount());
		sleep(3);
		vpEquals("Read label Appointment Type", "APPT", acp.readLblApptType());
		sleep(3);
		vpEquals("Read label DOB and SSN", "DOB:6/1/1994SSN (Last 4):1663", acp.readLblDateOfBirthSSN());
		sleep(3);
		vpEquals("Read label Time and Date", "9/20/2024, 8:00 AM - 9/20/2024, 8:30 AM", acp.readLblTimeAndDateAppt());
		sleep(5);
		vpEquals("Read label Time and Date", "9/20/2024, 8:00 AM - 9/20/2024, 8:30 AM",
				acp.readTimeSlotViewerSlotValueTimeFrame());
		sleep(5);

		// Verify the Patient SC Percent label displays on Appointment Request
		// Page
		vpEquals("Special Indtuctions label is displayed on Appointment Calendar Page", "SPECIAL INSTRUCTIONS",
				acp.readLblSpecialInstructions());
		sleep(3);
		// Verify theSpecial Instructions Info is displayed on Appointment Request Page

		vpEquals("Special Instructions Info is displayed on Appointment Request Page",
				"NEW PTS 60 MIN/EST PTS 30 MIN; OVERBOOKS MUSTPPROVED BY DR. HATTEL", acp.readTxtSpecialInstructions());
		sleep(3);
		vpEquals("Read clinic setup info", "Variable Length 30 Min Appt┃Max Overbook: 10┃Timezone: MOUNTAIN",
				acp.readHdrTxtClinicSetupInfo());
		sleep(3);

	}

}