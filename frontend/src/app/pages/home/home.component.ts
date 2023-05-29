import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ChildService } from 'src/app/services/child.service';
import { ParentsService } from 'src/app/services/parents.service';
import { Router } from '@angular/router';

export interface IParent {
  id: number,
  sender: string,
  receiver: string,
  totalAmount: number,
  childs:IChild[]
}
export enum Direction {
  ASCENDING = 'asc',
  DESCENDING = 'desc'
}

export interface IChild {
  id: number,
  parentId: number,
  paidAmount: number
}

export interface ITableData {
  id: number,
  _id: number,
  sender: string,
  receiver: string,
  totalAmount: number,
  totalPaidAmount: number,
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  parents:IParent[] = [];
  childs:any;
  tableData: ITableData[] = [];
  _dataSource:any = new MatTableDataSource([]);
  length:number = 0;
  displayedColumns = ['id', 'sender', "receiver", "totalAmount", "totalPaidAmount"];
  loading:boolean = false;

  currentPage:number = 1; 
  @ViewChild(MatPaginator)  paginator: MatPaginator | undefined;
  @ViewChild(MatSort) sort: MatSort = new MatSort;

  // A boolean flag that indicates whether the panel is open or not.
  public panelOpenState = false;
  // The initial direction for sorting the parent IDs is set to ascending.
  public direction = Direction.ASCENDING;
  
  constructor(
    private parentService: ParentsService,
    private childService: ChildService,
    private route: Router
  ) {
  }

  ngOnInit() {
 this.loadData();
  }

  loadData(){
    this.loading = true;
    this.tableData = [];
    this.parentService.getAllParents(this.currentPage,this.direction).subscribe((res:any) => {
      this.parents = res.pageList;
      for(let i=0; i < this.parents.length; i ++) {
        let sum = 0;
        let item = this.parents[i];
        item?.childs.forEach(element => {
          sum += element.paidAmount;
        });
        this.tableData.push({
          id: i + 1 * ((this.currentPage -1) *2),
          _id: item.id,
          sender: item.sender,
          receiver: item.receiver,
          totalAmount: item.totalAmount,
          totalPaidAmount: sum
        });
       
      }

      this._dataSource.data = this.tableData;
      this.length = res.nrOfElements;
      // this._dataSource.paginator = this.paginator;
      // this._dataSource.sort = this.sort;
      this.loading = false;

      console.log("this.length",this.length);
    });
  }

  public get dataSource():any {
    if(this.loading || this.tableData.length == 0) return [];
    return this._dataSource;
  }

  public executeSelectedChange = (event: any) => {
    console.log(event);
  }

  public onRowClicked(row:ITableData) {
    // console.log('Row clicked: ', row);
    this.route.navigate(['detail'], {queryParams:{id: row._id}});
  }

  onPaginateChange(event:any){
    console.log(event);

    this.currentPage = event.pageIndex+1;
    this.loadData();
  }

  public onOrderChangeAscending(): void {
    this.direction = (Direction.ASCENDING);
    this.loadData();
  }
  public onOrderChangeDescending(): void { 
    this.direction  = (Direction.DESCENDING);
    this.loadData();
  }

  ngModelChange(event:any){
    this.loadData();
  }
}
