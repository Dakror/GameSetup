package de.dakror.gamesetup.util;



public class Vector3D {
	public float x;
	public float y;
	public float z;
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D add(Vector3D o) {
		x += o.x;
		y += o.y;
		z += o.z;
		
		return this;
	}
	
	public Vector3D sub(Vector3D o) {
		x -= o.x;
		y -= o.y;
		z -= o.z;
		
		return this;
	}
	
	public float getLength() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public void setLength(float length) {
		float fac = length / getLength();
		x *= fac;
		y *= fac;
		z *= fac;
	}
	
	@Override
	public Vector3D clone() {
		return new Vector3D(x, y, z);
	}
	
	public Vector3D normalize() {
		setLength(1);
		
		return this;
	}
	
	public float getDistance(Vector3D o) {
		return clone().sub(o).getLength();
	}
	
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public Vector3D mul(float size) {
		x *= size;
		y *= size;
		z *= size;
		
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector) {
			Vector3D v = (Vector3D) obj;
			return x == v.x && y == v.y && z == v.z;
		}
		return false;
	}
	
	public float dot(Vector3D o) {
		return x * o.x + y * o.y + z * o.z;
	}
}
