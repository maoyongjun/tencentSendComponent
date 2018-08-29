package org.foxconn.tencent.sendComponent.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Component{
	//解析根节点
	private Component system;
	
	//写入数据库时记录ID
	private String id;
	private String parentId;
	private Date lasteditdt;
	
	private String sn;
	private String pn;
	private String fw;
	private String type;
	
	private Component board;
	private List<Component> cpu= new ArrayList<Component>();
	private List<Component> hdd= new ArrayList<Component>();
	private List<Component> memory= new ArrayList<Component>();
	private List<Component> nic= new ArrayList<Component>();
	private List<Component> psu= new ArrayList<Component>();
	private Component raid;
	private Component hba;
	private List<Component> bp= new ArrayList<Component>();
	
	private List<Component> component= new ArrayList<Component>();

	public Component getSystem() {
		return system;
	}

	public void setSystem(Component system) {
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

	public Component getBoard() {
		return board;
	}

	public void setBoard(Component board) {
		if(null!=board){
			board.setType("主板");
			component.add(board);
		}
		
		this.board = board;
	}

	public List<Component> getCpu() {
		return cpu;
	}

	public void setCpu(List<Component> cpu) {
		if(cpu.size()>0){
			cpu.get(0).setType("CPU");
			component.addAll(cpu);
		}
		this.cpu = cpu;
	}

	public List<Component> getHdd() {
		return hdd;
	}

	public void setHdd(List<Component> hdd) {
		if(hdd.size()>0){
			hdd.get(0).setType("硬盘");
			component.addAll(hdd);
		}
		this.hdd = hdd;
	}

	public List<Component> getMemory() {
		return memory;
	}

	public void setMemory(List<Component> memory) {
		if(memory.size()>0){
			memory.get(0).setType("内存");
			component.addAll(memory);
		}
		this.memory = memory;
	}

	public List<Component> getNic() {
		return nic;
	}

	public void setNic(List<Component> nic) {
		if(nic.size()>0){
			nic.get(0).setType("网卡");
			component.addAll(nic);
		}
		this.nic = nic;
	}

	public List<Component> getPsu() {
		return psu;
	}

	public void setPsu(List<Component> psu) {
		if(psu.size()>0){
			psu.get(0).setType("电源");
			component.addAll(psu);
		}
		this.psu = psu;
	}

	public Component getRaid() {
		return raid;
	}

	public void setRaid(Component raid) {
		if(null!=raid){
			raid.setType("RAID卡");
			component.add(raid);
		}
		this.raid = raid;
	}

	public Component getHba() {
		return hba;
	}

	public void setHba(Component hba) {
		if(null!=hba){
			hba.setType("HBA卡");
			component.add(hba);
		}
		this.hba = hba;
	}

	public List<Component> getBp() {
		return bp;
	}

	public void setBp(List<Component> bp) {
		if(bp.size()>0){
			bp.get(0).setType("背板");
			component.addAll(bp);
		}
		this.bp = bp;
	}



	public List<Component> getComponent() {
		return component;
	}

	public void setComponent(List<Component> component) {
		this.component = component;
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
