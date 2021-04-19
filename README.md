# S&E Real Estate Agency


## Project Overview
You are required to analyse, design, implement and test a stand-alone prototype for S&E Real Estate Agency, 
which manages the rental and sale of properties. In this initial stand-alone system you are not required to allow 
concurrent or web access; however you are required to design the system in a way that allows for extensibility, 
maintainability and reusability. You should also ensure all user accounts are password protected and can access or 
edit only the information related to their role and identity. Assume staff are already registered in the system with 
their e-number and password. The public should be allowed to view and search for Property and Open Day details 
specifying suburb and price range. You are not required to handle property images at this stage.
All client queries must be directed to your product owner, your tutor in this project. You may also post general 
questions to the Canvas Discussion Board. 


## Requirements:

### Customers
* All customers must supply their name and email address when they register and specify whether they are 
vendors (selling), landlords (leasing) or whether they are interested in buying or renting properties. All 
customers are given a unique ID if registration is successful.
* Those customers buying or renting properties are to be allowed to add suburbs that are of interest to 
them. Such customers are to be informed whenever a new property is listed (for rent or sale) in a suburb 
that is of interest to them. They should also be notified when an inspection is created and cancelled.
* Those customers selling or leasing properties are allowed to add their properties to the system and edit 
the details, however, the properties are not listed until an employee is assigned to sell or rent it.
Employees
* The company currently has a number of full-time and part-time employees. All employees are paid a salary. 
Part-timers are paid a salary that is proportional to the hours they worked. e.g. a half-time employee gets 
50% of the full time salary. Part-timers are required to enter their hours into the system each month and 
get the branch manager to approve it.
* When an owner adds a new sale or rental property, the branch manager inspects the necessary documents 
handed over (physically) before assigning a single employee to it.
* Sales consultants are tasked with selling a property on behalf of a vendor. They are paid a base salary plus 
a bonus which is 40% of the commission S&E receives from the final sale price of each house the consultant 
sells. Sales consultants advertise the property, liaise with the vendor, organise legal documents, conduct 
inspections and facilitate negotiations between the vendor and potential buyers.
* Property managers are tasked with the long term management of rental properties on behalf of a landlord. 
The tasks of the property manager include advertising the property, conducting inspections, reviewing 
tenant applications, checking maintenance reports, organising maintenance works, paying bills from 
property accounts and deducting management fees from property accounts.
* The branch administrator does various office duties including receiving documents, scanning documents 
into the system, collecting rent, crediting the branch account with money received, and running the payroll 
at the end of each month. The payroll includes salaries for all staff, commissions on property sales and 
payments to landlords.


### Inspections
* Each sales consultant and property manager is required to schedule inspections for the properties assigned 
to them. They must specify the property, date and time for each inspection that they have arranged.
PROJECT OPTION 2: Page 1/4Properties
* All properties must specify property address, suburb, and the property capacity (number of 
bedrooms/bathrooms/car spaces)
* Properties come in various types (house, unit, flat, townhouse, and studio).
For Sale Properties
* The sales commission is negotiated between the sales consultant and the vendors from 2 to 5% of the final 
selling price. All properties sold by S&E Real Estate Agency are done either through negotiation or auction.
* Before conducting any inspections a “Section 32” must be compiled by a legal professional. This is scanned 
into the system and then copies are made available to buyers.


### Negotiation:
* The vendors are required to specify the minimum price they are willing to consider. A vendor can increase 
the minimum amount any time (reflecting the change in property values), unless it is for sale by auction.
* When a formal offer is made on a property (that exceeds the minimum amount), the vendor has 3 days to 
either accept or reject the offer. The buyer can withdraw (i.e. undo) the offer at any time during this period 
and other offers can be taken during this period. An offer is implicitly rejected by the vendor if they do not 
respond within the 3 day period. During the 3 day period inspections can still be conducted.
* Whenever an offer is accepted by the vendor, the buyer is required to make a 10% down-payment within 
24 hours. No further offers can be taken during this 24 hour period. The buyer making the offer can change 
their mind on a purchase up until the deposit is paid.
* When the deposit is received the property is considered “under contract” and all future inspections are 
then cancelled. If the deposit is not received within 24 hours then the property stays on the market and 
new offers can be taken. During the 24 hour period inspections can still be conducted.


### Auction:
* Vendors are required to select an auction date and properties that are up for auction can only be booked 
for inspection for dates before the auction date.
* The vendors may choose to specify the minimum reserve price for the auction (or may choose to have no 
reserve). Once the bidding process has begun, the minimum reserve can no longer be altered.
* When a bid is made, it must exceed any previous bid on the property by at least $1000. The auction ends 
when there have not been any bids for 30 seconds or more.
* When the auction ends, the bidder with the highest bid (that meets any minimum reserve price) is given 24 
hours to give a deposit of 10%. If the highest bidder fails to provide a deposit within 24 hours, then the 
next highest bid is considered (again assuming it has met any minimum reserve).
* This process repeats until there are either no valid bids left (or no bids that meet the minimum reserve).
* A property that fails to sell under auction, can be put up for auction again (however any minimum reserve 
must be reduced by at least $10,000 or removed all together, no reserve can be added to a property that 
has previously failed to sell without a reserve).
* When the deposit is received the property is considered “under contract” and cannot be put up for sale or 
auction again until the sale is finalised.
* The management fee for a property is usually a standard 8% of the rental amount. If the same landlord has 
two or more properties with S&E then a discount rate is automatically given which is 7% for each property. 
The management fee can also be negotiated down further to a minimum of 7% for a single property and 
6% for multiple properties.
* Landlords offering properties for rent must specify the weekly rental and acceptable contract durations 
(e.g. 6 months, 1 year or 2 years). Potential renters must make an application specifying the weekly rental 
and contract duration desired. These may be different from the rent and duration on the offer.
* There must be at least one applicant on each application. Personal details such as each applicant’s income, 
occupation, current and past employers/rental contracts, length of current/ past employment/rental etc. 
are captured in the system. These may be edited at any time by the customer.
* When an application is received the landlord has 3 days to either accept or reject the application. Any 
applicant can withdraw (i.e. undo) the offer at any time during this period and other applications can be 
taken during this period. An application is implicitly rejected by the landlord by not responding within the 3 
day period. During the 3 day period inspections can still be conducted.
* When an application is accepted the applicant(s) have 24 hours to pay one month’s rent in advance plus a 
once off payment called the “bond”. The bond is calculated as 4 weeks rent and this amount must be 
immediately paid into the REIV trust account. No further applications can be taken during this 24 hour 
period. The applicant can change their mind up until the rent and bond is paid.
* When the rent and bond is received within the 24 hour period the property is considered “let”, the applicant 
becomes the tenant and all future inspections are cancelled. If the rent and bond is not received within 24 
hours then the property stays on the market and new applications can be taken. During the 24 hour period 
inspections can still be conducted.
Scope can be roughly defined as:
1. In scope are all system functions related to listing, searching, offering/accepting, and payment of salary, 
rent, deposits and commissions. Customer details must be captured if they are involved in buying or 
renting.
2. Out of scope – Functionality related to property tax, first home buyers grants and insurance.
3. Limited scope – Functionality of external systems with which the new system must interface such as legal 
services for compiling section 32s, marketing services for advertising properties in papers and with bill 
boards etc. You may need to show that these tasks are done, but no structural data relating to those 
aspects are to be maintained in your system.


### Sales Requirements
Authorized users (manager, employee assigned) should be allowed to view the current state of any property 
currently in the market. A property is not considered sold when the deposit is paid. It is only sold when the full 
sale price has been paid by the buyer to the vendor which is called ‘settlement’. Modelling settlement (i.e. 
paying parts of the purchase price to creditors such as banks and lawyers) is out of scope.
### Rental Requirements
Authorized users (manager, employee assigned) should be allowed to view the current state of any property 
listed for rental or currently being rented. In the life time of the property there may be many different tenants 
and applications for tenancy. The rent may only be changed after the term of the lease expires.

The current auction system is processed centrally with bids being manually recorded in the system by the 
auctioneer. In the full system, there needs to be networked client applications that can be used to submit bids 
remotely to the central server where the details of the bid are stored and the current highest bid details are 
transmitted to all the clients.
