package com.example.demosecurity.batchJob;

import com.example.demosecurity.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class PersonItemProcessor implements ItemProcessor<PersonDTO, Person> {
    private static final Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final PersonDTO personDTO) throws Exception {
         String firstName = personDTO.getFirst();
         String lastName = personDTO.getFirst();
         int age = personDTO.getAge();
         double gpa = personDTO.getGpa();

         int year = LocalDate.now().getYear();
         age = year - age;

        Person transformedPerson = new Person(firstName,lastName, gpa, LocalDate.of(age,1,1));
        logger.info("Value: "+ transformedPerson);
        return transformedPerson;
    }
}
