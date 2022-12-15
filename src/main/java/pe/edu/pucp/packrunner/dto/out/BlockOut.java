package pe.edu.pucp.packrunner.dto.out;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Block;

import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class BlockOut {

    private long idBlock;
    private Date startDate;
    private Date endDate;
    private EdgeOut edge;

    public BlockOut(Block block) {
        this.idBlock = block.getId();
        this.startDate = block.getStartDate();
        this.endDate = block.getEndDate();
        this.edge = new EdgeOut(block.getEdge());
    }

}
