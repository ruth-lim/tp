package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.AttributeContainsKeywordsPredicate;
import seedu.address.model.Model;
import seedu.address.model.person.patients.Patient;

/**
 * Filters the list of patients based on specific criteria and updates the filtered list.
 */
public class FilterPatientCommand extends Command {

    public static final String COMMAND_WORD = "filter-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the list of patients by a specific criteria.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " Root Canal";

    private final String attribute;
    private final String keywords;

    /**
     * Constructs a FilterPatientCommand to filter a patient based on the provided attribute and keywords.
     *
     * @param attribute The predicate by which the patient should be filtered.
     * @param keywords  A list of keywords to match against the specified attribute.
     */
    public FilterPatientCommand(String attribute, String keywords) {
        this.attribute = attribute;
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model) {
        Predicate<Patient> predicate = new AttributeContainsKeywordsPredicate(attribute, keywords);
        model.updateFilteredPatientList(predicate);

        if (model.getFilteredPatientList().isEmpty()) {
            String errorMessage = String.format("No patients found with the %s: %s!", attribute, keywords);
            return new CommandResult(String.format(errorMessage, keywords));
        }

        return new CommandResult("Filtered patients by " + attribute + " with: "
            + keywords);
    }

    /**
     * Checks if this FilterPatientCommand is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPatientCommand)) {
            return false;
        }

        FilterPatientCommand otherFilterPatientCommand = (FilterPatientCommand) other;
        return attribute.equals(otherFilterPatientCommand.attribute)
            && keywords.equals(otherFilterPatientCommand.keywords);
    }

    /**
     * Returns a string representation of this FilterPatientCommand.
     *
     * @return A string containing information about the command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("attribute", attribute)
            .add("keywords", keywords)
            .toString();
    }
}