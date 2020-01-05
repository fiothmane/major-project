package emse.anass.faircorp.models;

public class Ringer {

    private Long id;

    private Integer level;

    private Status status;

    private Room room;

    public Ringer() {

    }

    public Ringer(Long id, Integer level, Status status, Room room) {
        this.id = id;
        this.level = level;
        this.status = status;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}