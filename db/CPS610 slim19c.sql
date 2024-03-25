select G.Grade, C.Credit_hours
from STUDENT S, GRADE_REPORT G, SECTION SEC, COURSE C
where G.Student_number=S.Student_number AND
G.Section_identifier=SEC.Section_identifier AND
SEC.Course_number=C.Course_number AND S.Name='James';