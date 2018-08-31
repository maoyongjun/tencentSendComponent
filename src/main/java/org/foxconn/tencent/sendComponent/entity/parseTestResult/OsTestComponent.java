package org.foxconn.tencent.sendComponent.entity.parseTestResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OsTestComponent{
	//解析根节点
	private OsTestComponent system;
	
	//写入数据库时记录ID
	private String id;
	private String parentId;
	private Date lasteditdt;
	
	private String sn;
	private String pn;
	private String fw;
	private String type;
	
	private OsTestComponent board;
	private List<OsTestComponent> cpu= new ArrayList<OsTestComponent>();
	private List<OsTestComponent> hdd= new ArrayList<OsTestComponent>();
	private List<OsTestComponent> memory= new ArrayList<OsTestComponent>();
	private List<OsTestComponent> nic= new ArrayList<OsTestComponent>();
	private List<OsTestComponent> psu= new ArrayList<OsTestComponent>();
	private OsTestComponent raid;
	private OsTestComponent hba;
	private List<OsTestComponent> bp= new ArrayList<OsTestComponent>();
	
	private List<OsTestComponent> component= new ArrayList<OsTestComponent>();

	public OsTestComponent getSystem() {
		return system;
	}

	public void setSystem(OsTestComponent system) {
		this.system = system;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getFw() {
		return fw;
	}

	public void setFw(String fw) {
		this.fw = fw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public OsTestComponent getBoard() {
		return board;
	}

	public void setBoard(OsTestComponent board) {
		if(null!=board){
			board.setType("主板");
			component.add(board);
		}
		
		this.board = board;
	}

	public List<OsTestComponent> getCpu() {
		return cpu;
	}

	public void setCpu(List<OsTestComponent> cpu) {
		if(cpu.size()>0){
			cpu.get(0).setType("CPU");
			component.addAll(cpu);
		}
		this.cpu = cpu;
	}

	public List<OsTestComponent> getHdd() {
		return hdd;
	}

	public void setHdd(List<OsTestComponent> hdd) {
		if(hdd.size()>0){
			hdd.get(0).setType("硬盘");
			component.addAll(hdd);
		}
		this.hdd = hdd;
	}

	public List<OsTestComponent> getMemory() {
		return memory;
	}

	public void setMemory(List<OsTestComponent> memory) {
		if(memory.size()>0){
			memory.get(0).setType("内存");
			component.addAll(memory);
		}
		this.memory = memory;
	}

	public List<OsTestComponent> getNic() {
		return nic;
	}

	public void setNic(List<OsTestComponent> nic) {
		if(nic.size()>0){
			nic.get(0).setType("网卡");
			component.addAll(nic);
		}
		this.nic = nic;
	}

	public List<OsTestComponent> getPsu() {
		return psu;
	}

	public void setPsu(List<OsTestComponent> psu) {
		if(psu.size()>0){
			psu.get(0).setType("电源");
			component.addAll(psu);
		}
		this.psu = psu;
	}

	public OsTestComponent getRaid() {
		return raid;
	}

	public void setRaid(OsTestComponent raid) {
		if(null!=raid){
			raid.setType("RAID卡");
			component.add(raid);
		}
		this.raid = raid;
	}

	public OsTestComponent getHba() {
		return hba;
	}

	public void setHba(OsTestComponent hba) {
		if(null!=hba){
			hba.setType("HBA卡");
			component.add(hba);
		}
		this.hba = hba;
	}

	public List<OsTestComponent> getBp() {
		return bp;
	}

	public void setBp(List<OsTestComponent> bp) {
		if(bp.size()>0){
			bp.get(0).setType("背板");
			component.addAll(bp);
		}
		this.bp = bp;
	}



	public List<OsTestComponent> getComponent() {
		return component;
	}

	public void setComponent(List<OsTestComponent> OsTestComponent) {
		this.component = OsTestComponent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getLasteditdt() {
		return lasteditdt;
	}

	public void setLasteditdt(Date lasteditdt) {
		this.lasteditdt = lasteditdt;
	}

	@Override
	public String toString() {
		return "Component [system=" + system + ", id=" + id + ", parentId=" + parentId + ", sn=" + sn + ", pn=" + pn
				+ ", fw=" + fw + ", type=" + type + ", board=" + board + ", cpu=" + cpu + ", hdd=" + hdd + ", memory="
				+ memory + ", nic=" + nic + ", psu=" + psu + ", raid=" + raid + ", hba=" + hba + ", bp=" + bp
				+ ", component=" + component + "]";
	}

	
	
}
