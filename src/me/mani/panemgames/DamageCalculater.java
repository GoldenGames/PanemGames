package me.mani.panemgames;


public class DamageCalculater {

	private double totalDamage = 0.0;
	
	public DamageCalculater(int baseDamage, DamageModifier... damageModifier) {
		totalDamage = (double) baseDamage;
		for (DamageModifier modifier : damageModifier)
			totalDamage = totalDamage*(1.0 + (modifier.getPower() / 100));	
	}
	
	public DamageCalculater(int baseDamage) {
		totalDamage = (double) baseDamage;
	}
	
	public double getTotalDamage() {
		return totalDamage;
	}
	
	public abstract class DamageModifier {
		
		private DamageModifierType type;
		private double power;
		
		public DamageModifier(DamageModifierType type, double power) {
			this.type = type;
			this.power = power;
		}
		
		public DamageModifierType getType() {
			return type;
		}
		
		public double getPower() {
			return power;
		}
		
	}
	
	public enum DamageModifierType {
		ATTACKER_ADVANTAGE,
		ATTACKED_ADVANTAGE,
		ATTACKER_DISADVANTAGE,
		ATTACKED_DISADVANTAGE,
		NATURE,
		SPECIAL;
	}
}
