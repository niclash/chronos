/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.location.assembly;

import org.qi4j.api.entity.EntityBuilder;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.Activatable;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.chronos.domain.model.location.country.Country;
import org.qi4j.chronos.domain.model.location.country.CountryRepository;
import org.qi4j.chronos.domain.model.location.country.CountryState;

@Mixins( CountryBootstrap.CountryBootstrapMixin.class )
interface CountryBootstrap extends Activatable, ServiceComposite
{
    public class CountryBootstrapMixin
        implements Activatable
    {
        static final String[][] DATA = {
            { "Afghanistan", "004", "AFG", "AF" },
            { "Åland Islands", "248", "ALA", "AX" },
            { "Albania", "008", "ALB", "AL" },
            { "Algeria", "012", "DZA", "DZ" },
            { "American Samoa", "016", "ASM", "AS" },
            { "Andorra", "020", "AND", "AD" },
            { "Angola", "024", "AGO", "AO" },
            { "Anguilla", "660", "AIA", "AI" },
            { "Antarctica", "010", "ATA", "AQ" },
            { "Antigua and Barbuda", "028", "ATG", "AG" },
            { "Argentina", "032", "ARG", "AR" },
            { "Armenia", "051", "ARM", "AM" },
            { "Aruba", "533", "ABW", "AW" },
            { "Australia", "036", "AUS", "AU" },
            { "Austria", "040", "AUT", "AT" },
            { "Azerbaijan", "031", "AZE", "AZ" },
            { "Bahamas", "044", "BHS", "BS" },
            { "Bahrain", "048", "BHR", "BH" },
            { "Bangladesh", "050", "BGD", "BD" },
            { "Barbados", "052", "BRB", "BB" },
            { "Belarus", "112", "BLR", "BY" },
            { "Belgium", "056", "BEL", "BE" },
            { "Belize", "084", "BLZ", "BZ" },
            { "Benin", "204", "BEN", "BJ" },
            { "Bermuda", "060", "BMU", "BM" },
            { "Bhutan", "064", "BTN", "BT" },
            { "Bolivia", "068", "BOL", "BO" },
            { "Bosnia and Herzegovina", "070", "BIH", "BA" },
            { "Botswana", "072", "BWA", "BW" },
            { "Flag of Norway Bouvet Island", "074", "BVT", "BV" },
            { "Brazil", "076", "BRA", "BR" },
            { "British Indian Ocean Territory", "086", "IOT", "IO" },
            { "Brunei Darussalam", "096", "BRN", "BN" },
            { "Bulgaria", "100", "BGR", "BG" },
            { "Burkina Faso", "854", "BFA", "BF" },
            { "Burundi", "108", "BDI", "BI" },
            { "Cambodia", "116", "KHM", "KH" },
            { "Cameroon", "120", "CMR", "CM" },
            { "Canada", "124", "CAN", "CA" },
            { "Cape Verde", "132", "CPV", "CV" },
            { "Cayman Islands", "136", "CYM", "KY" },
            { "Central African Republic", "140", "CAF", "CF" },
            { "Chad", "148", "TCD", "TD" },
            { "Chile", "152", "CHL", "CL" },
            { "China", "156", "CHN", "CN" },
            { "Christmas Island", "162", "CXR", "CX" },
            { "Cocos (Keeling) Islands", "166", "CCK", "CC" },
            { "Colombia", "170", "COL", "CO" },
            { "Comoros", "174", "COM", "KM" },
            { "Congo", "178", "COG", "CG" },
            { "Congo, Democratic Republic of the", "180", "COD", "CD" },
            { "Cook Islands", "184", "COK", "CK" },
            { "Costa Rica", "188", "CRI", "CR" },
            { "Côte d'Ivoire", "384", "CIV", "CI" },
            { "Croatia", "191", "HRV", "HR" },
            { "Cuba", "192", "CUB", "CU" },
            { "Cyprus", "196", "CYP", "CY" },
            { "Czech Republic", "203", "CZE", "CZ" },
            { "Denmark", "208", "DNK", "DK" },
            { "Djibouti", "262", "DJI", "DJ" },
            { "Dominica", "212", "DMA", "DM" },
            { "Dominican Republic", "214", "DOM", "DO" },
            { "Ecuador", "218", "ECU", "EC" },
            { "Egypt", "818", "EGY", "EG" },
            { "El Salvador", "222", "SLV", "SV" },
            { "Equatorial Guinea", "226", "GNQ", "GQ" },
            { "Eritrea", "232", "ERI", "ER" },
            { "Estonia", "233", "EST", "EE" },
            { "Ethiopia", "231", "ETH", "ET" },
            { "Falkland Islands (Malvinas)", "238", "FLK", "FK" },
            { "Faroe Islands", "234", "FRO", "FO" },
            { "Fiji", "242", "FJI", "FJ" },
            { "Finland", "246", "FIN", "FI" },
            { "France", "250", "FRA", "FR" },
            { "French Guiana", "254", "GUF", "GF" },
            { "French Polynesia", "258", "PYF", "PF" },
            { "French Southern Territories", "260", "ATF", "TF" },
            { "Gabon", "266", "GAB", "GA" },
            { "Gambia", "270", "GMB", "GM" },
            { "Georgia", "268", "GEO", "GE" },
            { "Germany", "276", "DEU", "DE" },
            { "Ghana", "288", "GHA", "GH" },
            { "Gibraltar", "292", "GIB", "GI" },
            { "Greece", "300", "GRC", "GR" },
            { "Greenland", "304", "GRL", "GL" },
            { "Grenada", "308", "GRD", "GD" },
            { "Guadeloupe", "312", "GLP", "GP" },
            { "Guam", "316", "GUM", "GU" },
            { "Guatemala", "320", "GTM", "GT" },
            { "Guernsey", "831", "GGY", "GG" },
            { "Guinea", "324", "GIN", "GN" },
            { "Guinea-Bissau", "624", "GNB", "GW" },
            { "Guyana", "328", "GUY", "GY" },
            { "Haiti", "332", "HTI", "HT" },
            { "Heard Island and McDonald Islands", "334", "HMD", "HM" },
            { "Holy See (Vatican City State)", "336", "VAT", "VA" },
            { "Honduras", "340", "HND", "HN" },
            { "Hong Kong", "344", "HKG", "HK" },
            { "Hungary", "348", "HUN", "HU" },
            { "Iceland", "352", "ISL", "IS" },
            { "India", "356", "IND", "IN" },
            { "Indonesia", "360", "IDN", "ID" },
            { "Iran, Islamic Republic of", "364", "IRN", "IR" },
            { "Iraq", "368", "IRQ", "IQ" },
            { "Ireland", "372", "IRL", "IE" },
            { "Isle of Man", "833", "IMN", "IM" },
            { "Israel", "376", "ISR", "IL" },
            { "Italy", "380", "ITA", "IT" },
            { "Jamaica", "388", "JAM", "JM" },
            { "Japan", "392", "JPN", "JP" },
            { "Jersey", "832", "JEY", "JE" },
            { "Jordan", "400", "JOR", "JO" },
            { "Kazakhstan", "398", "KAZ", "KZ" },
            { "Kenya", "404", "KEN", "KE" },
            { "Kiribati", "296", "KIR", "KI" },
            { "Korea, Democratic People's Republic of", "408", "PRK", "KP" },
            { "Korea, Republic of", "410", "KOR", "KR" },
            { "Kuwait", "414", "KWT", "KW" },
            { "Kyrgyzstan", "417", "KGZ", "KG" },
            { "Lao People's Democratic Republic", "418", "LAO", "LA" },
            { "Latvia", "428", "LVA", "LV" },
            { "Lebanon", "422", "LBN", "LB" },
            { "Lesotho", "426", "LSO", "LS" },
            { "Liberia", "430", "LBR", "LR" },
            { "Libyan Arab Jamahiriya", "434", "LBY", "LY" },
            { "Liechtenstein", "438", "LIE", "LI" },
            { "Lithuania", "440", "LTU", "LT" },
            { "Luxembourg", "442", "LUX", "LU" },
            { "Macao", "446", "MAC", "MO" },
            { "Macedonia, the former Yugoslav Republic of", "807", "MKD", "MK" },
            { "Madagascar", "450", "MDG", "MG" },
            { "Malawi", "454", "MWI", "MW" },
            { "Malaysia", "458", "MYS", "MY" },
            { "Maldives", "462", "MDV", "MV" },
            { "Mali", "466", "MLI", "ML" },
            { "Malta", "470", "MLT", "MT" },
            { "Marshall Islands", "584", "MHL", "MH" },
            { "Martinique", "474", "MTQ", "MQ" },
            { "Mauritania", "478", "MRT", "MR" },
            { "Mauritius", "480", "MUS", "MU" },
            { "Mayotte", "175", "MYT", "YT" },
            { "Mexico", "484", "MEX", "MX" },
            { "Micronesia, Federated States of", "583", "FSM", "FM" },
            { "Moldova", "498", "MDA", "MD" },
            { "Monaco", "492", "MCO", "MC" },
            { "Mongolia", "496", "MNG", "MN" },
            { "Montenegro", "499", "MNE", "ME" },
            { "Montserrat", "500", "MSR", "MS" },
            { "Morocco", "504", "MAR", "MA" },
            { "Mozambique", "508", "MOZ", "MZ" },
            { "Myanmar", "104", "MMR", "MM" },
            { "Namibia", "516", "NAM", "NA" },
            { "Nauru", "520", "NRU", "NR" },
            { "Nepal", "524", "NPL", "NP" },
            { "Netherlands", "528", "NLD", "NL" },
            { "Netherlands Antilles", "530", "ANT", "AN" },
            { "New Caledonia", "540", "NCL", "NC" },
            { "New Zealand", "554", "NZL", "NZ" },
            { "Nicaragua", "558", "NIC", "NI" },
            { "Niger", "562", "NER", "NE" },
            { "Nigeria", "566", "NGA", "NG" },
            { "Niue", "570", "NIU", "NU" },
            { "Norfolk Island", "574", "NFK", "NF" },
            { "Northern Mariana Islands", "580", "MNP", "MP" },
            { "Norway", "578", "NOR", "NO" },
            { "Oman", "512", "OMN", "OM" },
            { "Pakistan", "586", "PAK", "PK" },
            { "Palau", "585", "PLW", "PW" },
            { "Palestinian Territory, Occupied", "275", "PSE", "PS" },
            { "Panama", "591", "PAN", "PA" },
            { "Papua New Guinea", "598", "PNG", "PG" },
            { "Paraguay", "600", "PRY", "PY" },
            { "Peru", "604", "PER", "PE" },
            { "Philippines", "608", "PHL", "PH" },
            { "Pitcairn", "612", "PCN", "PN" },
            { "Poland", "616", "POL", "PL" },
            { "Portugal", "620", "PRT", "PT" },
            { "Puerto Rico", "630", "PRI", "PR" },
            { "Qatar", "634", "QAT", "QA" },
            { "Réunion", "638", "REU", "RE" },
            { "Romania", "642", "ROU", "RO" },
            { "Russian Federation", "643", "RUS", "RU" },
            { "Rwanda", "646", "RWA", "RW" },
            { "Saint Barthelemy  Saint Barthélemy", "652", "BLM", "BL" },
            { "Saint Helena", "654", "SHN", "SH" },
            { "Saint Kitts and Nevis", "659", "KNA", "KN" },
            { "Saint Lucia", "662", "LCA", "LC" },
            { "Saint Martin (French part)", "663", "MAF", "MF" },
            { "Saint Pierre and Miquelon", "666", "SPM", "PM" },
            { "Saint Vincent and the Grenadines", "670", "VCT", "VC" },
            { "Samoa", "882", "WSM", "WS" },
            { "San Marino", "674", "SMR", "SM" },
            { "Sao Tome and Principe", "678", "STP", "ST" },
            { "Saudi Arabia", "682", "SAU", "SA" },
            { "Senegal", "686", "SEN", "SN" },
            { "Serbia", "688", "SRB", "RS" },
            { "Seychelles", "690", "SYC", "SC" },
            { "Sierra Leone", "694", "SLE", "SL" },
            { "Singapore", "702", "SGP", "SG" },
            { "Slovakia", "703", "SVK", "SK" },
            { "Slovenia", "705", "SVN", "SI" },
            { "Solomon Islands", "090", "SLB", "SB" },
            { "Somalia", "706", "SOM", "SO" },
            { "South Africa", "710", "ZAF", "ZA" },
            { "South Georgia and the South Sandwich Islands", "239", "SGS", "GS" },
            { "Spain", "724", "ESP", "ES" },
            { "Sri Lanka", "144", "LKA", "LK" },
            { "Sudan", "736", "SDN", "SD" },
            { "Suriname", "740", "SUR", "SR" },
            { "Svalbard and Jan Mayen", "744", "SJM", "SJ" },
            { "Swaziland", "748", "SWZ", "SZ" },
            { "Sweden", "752", "SWE", "SE" },
            { "Switzerland", "756", "CHE", "CH" },
            { "Syrian Arab Republic", "760", "SYR", "SY" },
            { "Taiwan, Province of China", "158", "TWN", "TW" },
            { "Tajikistan", "762", "TJK", "TJ" },
            { "Tanzania, United Republic of", "834", "TZA", "TZ" },
            { "Thailand", "764", "THA", "TH" },
            { "Timor-Leste", "626", "TLS", "TL" },
            { "Togo", "768", "TGO", "TG" },
            { "Tokelau", "772", "TKL", "TK" },
            { "Tonga", "776", "TON", "TO" },
            { "Trinidad and Tobago", "780", "TTO", "TT" },
            { "Tunisia", "788", "TUN", "TN" },
            { "Turkey", "792", "TUR", "TR" },
            { "Turkmenistan", "795", "TKM", "TM" },
            { "Turks and Caicos Islands", "796", "TCA", "TC" },
            { "Tuvalu", "798", "TUV", "TV" },
            { "Uganda", "800", "UGA", "UG" },
            { "Ukraine", "804", "UKR", "UA" },
            { "United Arab Emirates", "784", "ARE", "AE" },
            { "United Kingdom", "826", "GBR", "GB" },
            { "United States", "840", "USA", "US" },
            { "United States United States Minor Outlying Islands", "581", "UMI", "UM" },
            { "Uruguay", "858", "URY", "UY" },
            { "Uzbekistan", "860", "UZB", "UZ" },
            { "Vanuatu", "548", "VUT", "VU" },
            { "Venezuela", "862", "VEN", "VE" },
            { "Viet Nam", "704", "VNM", "VN" },
            { "Virgin Islands, British", "092", "VGB", "VG" },
            { "Virgin Islands, U.S.", "850", "VIR", "VI" },
            { "Wallis and Futuna", "876", "WLF", "WF" },
            { "Western Sahara", "732", "ESH", "EH" },
            { "Yemen", "887", "YEM", "YE" },
            { "Zambia", "894", "ZMB", "ZM" },
            { "Zimbabwe", "716", "ZWE", "ZW" }
        };

        @Service private CountryRepository repository;
        @Structure private UnitOfWorkFactory uowf;

        public void activate()
            throws Exception
        {
            UnitOfWork uow = uowf.newUnitOfWork();

            try
            {
                for( String[] entry : DATA )
                {
                    String numeric = entry[ 1 ];
                    if( repository.findByNumericCode( numeric ) == null )
                    {
                        String countryName = entry[ 0 ];
                        String alpha3 = entry[ 2 ];
                        String alpha2 = entry[ 3 ];

                        createCountry( uow, countryName, numeric, alpha2, alpha3 );
                    }
                }
            }
            finally
            {
                uow.complete();
            }
        }

        private void createCountry( UnitOfWork uow, String countryName, String numeric, String alpha2, String alpha3 )
        {
            EntityBuilder<Country> countryBuilder = uow.newEntityBuilder( Country.class, numeric );
            CountryState countryState = countryBuilder.instanceFor( CountryState.class );
            countryState.countryCodeNumeric().set( numeric );
            countryState.countryCodeAlpha2().set( alpha2 );
            countryState.countryCodeAlpha3().set( alpha3 );
            countryState.name().set( countryName );

            countryBuilder.newInstance();
        }

        public void passivate()
            throws Exception
        {
            // Do nothing
        }
    }
}

