package name.richardson.james.bukkit.starterkit.utilities.formatters;

import name.richardson.james.bukkit.utilities.formatters.AbstractChoiceFormatter;

public class ItemCountChoiceFormatter extends AbstractChoiceFormatter {

	public ItemCountChoiceFormatter() {
		setFormats("none", "one", "many");
		setLimits(0,1,2);
	}

}
