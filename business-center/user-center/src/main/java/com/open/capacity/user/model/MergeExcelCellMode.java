package com.open.capacity.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeExcelCellMode {

    private int firstRow;

    private int lastRow;

    private int firstCol;

    private int lastCol;
}
