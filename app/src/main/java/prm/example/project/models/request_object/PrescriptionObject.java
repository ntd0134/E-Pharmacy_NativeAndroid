package prm.example.project.models.request_object;

import java.util.List;

public class PrescriptionObject {
    private String createdDate;
    private String doctorName;
    private String hospitalId;
    private String id;
    private List<PrescriptionDetailsRequestList> prescriptionDetailsRequestList;
    private String username;

    public PrescriptionObject() {
    }

    public PrescriptionObject(String createdDate, String doctorName, String hospitalId, String id, List<PrescriptionDetailsRequestList> prescriptionDetailsRequestList, String username) {
        this.createdDate = createdDate;
        this.doctorName = doctorName;
        this.hospitalId = hospitalId;
        this.id = id;
        this.prescriptionDetailsRequestList = prescriptionDetailsRequestList;
        this.username = username;
    }

    public PrescriptionObject(String createdDate, List<PrescriptionDetailsRequestList> prescriptionDetailsRequestList,String id, String username) {
        this.createdDate = createdDate;
        this.prescriptionDetailsRequestList = prescriptionDetailsRequestList;
        this.username = username;
        this.id = id;
        this.hospitalId = "17D/BV - 99";
        this.doctorName = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<PrescriptionDetailsRequestList> getPrescriptionDetailsRequestList() {
        return prescriptionDetailsRequestList;
    }

    public void setPrescriptionDetailsRequestList(List<PrescriptionDetailsRequestList> prescriptionDetailsRequestList) {
        this.prescriptionDetailsRequestList = prescriptionDetailsRequestList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class PrescriptionDetailsRequestList{
        private Integer dose;
        private String drugId;
        private Integer frequency;
        private Integer quantity;
        private String timeUnit;

        public PrescriptionDetailsRequestList() {
        }

        public PrescriptionDetailsRequestList(Integer dose, String drugId, Integer frequency, Integer quantity, String timeUnit) {
            this.dose = dose;
            this.drugId = drugId;
            this.frequency = frequency;
            this.quantity = quantity;
            this.timeUnit = timeUnit;
        }

        public PrescriptionDetailsRequestList(String drugId) {
            this.dose = 0;
            this.drugId = drugId;
            this.frequency = 0;
            this.quantity = 0;
            this.timeUnit = "";
        }

        public Integer getDose() {
            return dose;
        }

        public void setDose(Integer dose) {
            this.dose = dose;
        }

        public String getDrugId() {
            return drugId;
        }

        public void setDrugId(String drugId) {
            this.drugId = drugId;
        }

        public Integer getFrequency() {
            return frequency;
        }

        public void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(String timeUnit) {
            this.timeUnit = timeUnit;
        }
    }
}
