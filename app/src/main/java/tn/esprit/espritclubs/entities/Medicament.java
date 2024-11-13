package tn.esprit.espritclubs.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Medicament {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medicament_id")
    private int medicamentId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "dosage")
    private String dosage;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    @ColumnInfo(name = "description")
    private String description;



    public Medicament(String name, String dosage, int quantity, Date startDate, Date endDate, String description) {
        this.name = name;
        this.dosage = dosage;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(int medicamentId) {
        this.medicamentId = medicamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "medicamentId=" + medicamentId +
                ", name='" + name + '\'' +
                ", dosage='" + dosage + '\'' +
                ", quantity=" + quantity +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
